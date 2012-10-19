package net.galaxy.rfbee;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 6:59 PM
 */
public interface RFBee {

    void runCommandMode(CommandModeAccess commandMode) throws IOException;

    void send(ByteBuffer buffer);

    void registerReceiveCallback(ReceiveCallback receiveCallback);

}
