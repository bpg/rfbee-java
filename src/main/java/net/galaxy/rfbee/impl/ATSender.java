package net.galaxy.rfbee.impl;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 10:01 PM
 */
public class ATSender {

    private static Logger logger = LoggerFactory.getLogger(ATSender.class.getSimpleName());

    private final InputStream in;
    private final OutputStream out;

    public ATSender(InputStream in, OutputStream out) throws IOException {
        this.in = in;
        if (in.available() > 0) {
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            logger.warn("There are residual bytes in the input stream: {}: skipped", Hex.encodeHexString(buffer));

        }
        this.out = out;
    }

//    protected String send(String message) {
//
//    }

    ///** */
//public static class SerialReader implements Runnable {
//    InputStream in;
//
//    public SerialReader(InputStream in) {
//        this.in = in;
//    }
//
//    public void run() {
//        byte[] buffer = new byte[1024];
//        int len = -1;
//        try {
//            while ((len = this.in.read(buffer)) > -1) {
//                System.out.print(new String(buffer, 0, len));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
///** */
//public static class SerialWriter implements Runnable {
//    OutputStream out;
//
//    public SerialWriter(OutputStream out) {
//        this.out = out;
//    }
//
//    public void run() {
//        try {
//            int c = 0;
//            while ((c = System.in.read()) > -1) {
//                this.out.write(c);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
}
