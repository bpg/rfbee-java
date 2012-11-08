/*
 * Project: rfbee-java, file: RFBeeImpl.java
 * Copyright (C) 2012 Pavel Boldyrev <pboldyrev@gmail.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.galaxy.rfbee.impl;

import net.galaxy.rfbee.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 9:53 PM
 */
public class RFBeeImpl implements RFBee {

    private static final Logger logger = LoggerFactory.getLogger(RFBeeImpl.class.getSimpleName());

    private static final String CMD_MODE_START = "+++\n";
    private static final String CMD_MODE_START_RESP = "ok, starting cmd mode\r\n";
    private static final String CMD_MODE_STOP = "ATO0\r";

    private final Connection connection;
    private final CommandMode commandMode;
    private volatile ReceiveCallback receiveCallback;

    public RFBeeImpl(final int terminator) throws IOException {
        this.connection = new Connection();
        this.connection.open();
        this.commandMode = new CommandModeImpl(connection);

        runCommandMode(new CommandModeAccess() {
            @Override
            public void execute(CommandMode commandMode) {
                try {
                    logger.debug("RFbee HW version: [{}]", commandMode.getHardwareVersion());
                    logger.debug("RFbee FW version: [{}]", commandMode.getFirmwareVersion());
                    commandMode.setSerialOutputFormat(CommandMode.SerialOutputFormat.EXTENDED);
                } catch (Exception ex) {
                    logger.error("Exception while initializing RFbee", ex);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        logger.trace("waiting for data...");
//                        System.out.print(Integer.toHexString(connection.getInputStream().read()).toUpperCase());
//                        System.out.print(" ");

                        ReceivedMessage msg = ReceivedMessageFactory.getFromStream(connection.getInputStream());
                        logger.trace("message received: {}", msg);
                        for (int i = 0; i < msg.getData().length; i++) {
                            byte b = msg.getData()[i];
                            if ((b & 0xff) == terminator) {
                                byte[] payload = buffer.toByteArray();
                                if (receiveCallback != null) {
                                    receiveCallback.receive(payload);
                                } else {
                                    logger.trace("No callback -- discard received payload: `{}`",
                                            StringEscapeUtils.escapeJava(new String(payload)));
                                }
                                buffer.reset();
                            } else {
                                buffer.write(b);
                            }
                        }

                    } catch (Exception ex) {
                        logger.error("Error reading data. Reset", ex);
                    }
                }
                logger.warn("Reader terminated");
            }
        }, "READER").start();
        logger.info("RFbee initialized and ready");
    }

    @Override
    public synchronized void runCommandMode(CommandModeAccess commandModeAccess) throws IOException {
        connection.sendCmd(CMD_MODE_START, CMD_MODE_START_RESP);

        commandModeAccess.execute(commandMode);

        connection.sendCmd(CMD_MODE_STOP, "ok\r\n");

        connection.resetPort();
    }

    @Override
    public synchronized void send(ByteBuffer buffer) throws IOException {
        OutputStream os = connection.getOutputStream();
        os.write(buffer.array());
        os.flush();
    }

    @Override
    public void registerReceiveCallback(ReceiveCallback receiveCallback) {
        this.receiveCallback = receiveCallback;
    }

    @Override
    public synchronized void close() throws IOException {
        if (this.connection != null) {
            connection.close();
        }
    }
}
