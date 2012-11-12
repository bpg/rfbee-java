/*
 * Copyright (C) 2012 Pavel Boldyrev <pboldyrev@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
                SignalQuality avgSignalQuality = SignalQuality.NA;

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        logger.trace("waiting for data...");
//                        System.out.print(Integer.toHexString(connection.getInputStream().read()).toUpperCase());
//                        System.out.print(" ");

                        ReceivedMessage msg = ReceivedMessageFactory.getFromStream(connection.getInputStream());
                        avgSignalQuality = avgSignalQuality.average(msg.getSignalQuality());
                        logger.trace("message received: {}", msg);
                        for (int i = 0; i < msg.getPayload().length; i++) {
                            byte b = msg.getPayload()[i];
                            if ((b & 0xff) == terminator) {
                                byte[] data = buffer.toByteArray();
                                if (receiveCallback != null) {
                                    try {
                                        receiveCallback.receive(ReceivedMessageFactory.wrap(msg, data, avgSignalQuality));
                                    } catch (Throwable th) {
                                        logger.error("Error processing data in callback", th);
                                    }
                                } else {
                                    logger.trace("No callback -- discard the received data: `{}`",
                                            StringEscapeUtils.escapeJava(new String(data)));
                                }
                                buffer.reset();
                                avgSignalQuality = SignalQuality.NA;
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
