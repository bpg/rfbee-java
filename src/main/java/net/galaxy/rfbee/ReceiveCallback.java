package net.galaxy.rfbee;

import net.galaxy.rfbee.impl.ReceivedMessageImpl;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 8:22 PM
 */
public interface ReceiveCallback {

    void receive(ReceivedMessageImpl receiveMessage);
}
