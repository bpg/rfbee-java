package net.galaxy.rfbee.impl;

import net.galaxy.rfbee.CommandModeAccess;
import net.galaxy.rfbee.RFBee;
import net.galaxy.rfbee.ReceiveCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 9:53 PM
 */
public class RFBeeImpl implements RFBee {

    private final InputStream in;
    private final OutputStream out;

    public RFBeeImpl(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void runCommandMode(CommandModeAccess commandMode) throws IOException {

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(ByteBuffer buffer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerReceiveCallback(ReceiveCallback receiveCallback) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
