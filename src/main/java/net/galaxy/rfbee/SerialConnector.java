/*
 * Project: rfbee-java, file: SerialConnector.java
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

package net.galaxy.rfbee;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import net.galaxy.rfbee.impl.RFBeeImpl;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 8:41 PM
 */
public class SerialConnector {

    public RFBee connect(String portName, int speed) throws IOException {
        CommPort commPort;
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                throw new IllegalArgumentException("Port " + portName + " is not a serial port");
            }
            SerialPort serialPort = (SerialPort) commPort;
            serialPort.setSerialPortParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            return new RFBeeImpl(serialPort.getInputStream(), serialPort.getOutputStream());
        } catch (Exception ex) {
            throw new IOException("Unable to open comm port " + portName);

        }
    }
}
