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
