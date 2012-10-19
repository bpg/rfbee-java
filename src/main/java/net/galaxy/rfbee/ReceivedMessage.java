package net.galaxy.rfbee;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 8:30 PM
 */
public interface ReceivedMessage {
    int getSrc();

    int getDst();

    ByteBuffer getData();

    int getRssi();

    int getLqi();
}
