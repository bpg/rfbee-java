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

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 16/09/12 8:53 PM
 */
public class Connection {

    private static final Logger logger = LoggerFactory.getLogger(Connection.class.getSimpleName());

    private static final Charset ASCII = Charset.forName("ASCII");

    private SerialPort serialPort;

    private static final String PORT_OWNER = "WeatherStation";

    private static final String PORT_NAME = System.getProperty("serial.port.name", "/dev/ttyUSB*");
    private static final int PORT_BAUD_RATE = Integer.getInteger("serial.port.baud", 9600);
    private static final long CMD_RESPONSE_TIMEOUT = Integer.getInteger("rfbee.response.timeout", 100);

    private InputStream inputStream;
    private OutputStream outputStream;

    public static byte[] bytes(String str) {
        return str.getBytes(ASCII);
    }

    public void open() throws IOException {
        logger.info("Opening port...");
        Preconditions.checkArgument(!PORT_NAME.isEmpty());
        List<String> ports = new ArrayList<>();
        if (PORT_NAME.endsWith("*")) {
            for (int i = 0; i <= 9; i++) {
                ports.add(PORT_NAME.replace("*", String.valueOf(i)));
            }
        } else {
            ports.add(PORT_NAME);
        }

        for (String port : ports) {
            try {
                serialPort = openPort(port);
            } catch (Exception ex) {
                logger.info("Port {} is unavailable: " + ex.getMessage(), port);
                continue;
            }

            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            initializeSerialPort(serialPort);

            try {
                // reset port
                serialPort.setDTR(true);
                Thread.sleep(800);
                serialPort.setDTR(false);

                try {
                    assureResponse("ok\r\n");
                    break;
                } catch (IOException ex) {
                    logger.info("Not a RFbee on port {}", port);
                    serialPort = null;
                }
            } catch (Exception ex) {
                logger.debug("Exception while talking to device on serial port {}: {}", port, ex.getMessage());
            }
        }

        if (serialPort == null) {
            throw new IOException("No any serial port is available, requested port name is " + PORT_NAME);
        }
        logger.info("Connection to RFbee on port {} is ready", serialPort.getName());
    }

    private SerialPort openPort(String portName) throws IOException {
        Preconditions.checkNotNull(portName);

        logger.debug("Opening serial port: {}", portName);
        File file = new File(portName);
        if (!file.exists()) {
            throw new IOException("Port " + portName + " does not exist");
        }

        System.setProperty("gnu.io.rxtx.SerialPorts", portName);
        try {
            Enumeration<?> identifiers = CommPortIdentifier.getPortIdentifiers();
            logger.trace("Identifiers: {}", Joiner.on(";").join(Collections.list(identifiers)));
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            if (portIdentifier.isCurrentlyOwned()) {
                throw new IOException("Port is currently in use");
            }
            CommPort commPort = portIdentifier.open(PORT_OWNER, 0);
            if (!(commPort instanceof SerialPort)) {
                throw new IOException("Not a serial port");
            }
            return (SerialPort) commPort;
        } catch (Exception ex) {
            Throwables.propagateIfPossible(ex, IOException.class);
            throw new IOException(ex);
        }
    }

    public void close() {
        if (serialPort == null) {
            return;
        }

        try {
            serialPort.close();
        } catch (Exception e) {
            logger.debug("", e);
        } finally {
            serialPort = null;
            logger.info("Connection to RFbee closed");
        }
    }

    public InputStream getInputStream() throws IOException {
        if (serialPort == null) {
            throw new IOException("serial port is closed");
        }
        return inputStream;
    }

    public OutputStream getOutputStream() throws IOException {
        if (serialPort == null) {
            throw new IOException("serial port is closed");
        }
        return outputStream;
    }

    private void initializeSerialPort(final SerialPort serialPort) throws IOException {
        try {
            serialPort.setSerialPortParams(PORT_BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            logger.debug("Set baud rate: {}", PORT_BAUD_RATE);
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public String sendCmd(String cmd) throws IOException {
        Preconditions.checkNotNull(cmd);
        logger.trace("write string: `{}`", StringEscapeUtils.escapeJava(cmd));
        outputStream.write(bytes(cmd));
        outputStream.flush();
        return readResponse(0);
    }

    public void sendCmd(String cmd, String expectedResponse) throws IOException {
        Preconditions.checkNotNull(cmd);
        Preconditions.checkNotNull(expectedResponse);
        logger.trace("write string: `{}`", StringEscapeUtils.escapeJava(cmd));
        outputStream.write(bytes(cmd));
        outputStream.flush();

        if (!expectedResponse.isEmpty()) {
            assureResponse(expectedResponse);
        }
    }

    private String readResponse(int numBytesExpected) throws IOException {
        Stopwatch sw = new Stopwatch();
        StringBuilder response = new StringBuilder(numBytesExpected);

        while (true) {
            sw.reset();
            sw.start();
            logger.trace("waiting for {} bytes with {}ms timeout", numBytesExpected == 0 ? "any" : numBytesExpected, CMD_RESPONSE_TIMEOUT);
            while ((sw.elapsedMillis() < CMD_RESPONSE_TIMEOUT) &&
                    (numBytesExpected == 0 || (numBytesExpected > 0 && inputStream.available() < numBytesExpected))) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new IOException("Interrupted after " + sw.elapsedMillis() + "ms");
                }
            }

            if (inputStream.available() == 0) {
                break;
            }

            logger.trace("{} bytes are available after {}ms", inputStream.available(), sw.elapsedMillis());
            byte[] buff = new byte[128];
            int r = inputStream.read(buff);
            logger.trace("read {} bytes: [{}]", r, Hex.encodeHexString(Arrays.copyOfRange(buff, 0, r)));
            response.append(new String(buff, 0, r, ASCII));
            if (numBytesExpected > 0 && r == numBytesExpected && inputStream.available() == 0) {
                break;
            }
            numBytesExpected = 0; // after the first pass wait for anything remaining, for at least one cycle
        }

        return response.toString();
    }

    private void assureResponse(String expectedResponse) throws IOException {
        byte[] expBytes = bytes(expectedResponse);
        String receivedResponse = readResponse(expBytes.length);
        logger.trace("response `{}`", StringEscapeUtils.escapeJava(receivedResponse));
        logger.trace("expected `{}`", StringEscapeUtils.escapeJava(expectedResponse));
        if (!expectedResponse.equals(receivedResponse)) {
            throw new IOException("Unexpected response: `" + StringEscapeUtils.escapeJava(receivedResponse) + "`");
        }
    }

    public void resetPort() throws IOException {
        close();
        open();
    }
}
