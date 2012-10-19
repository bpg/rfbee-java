package net.galaxy.rfbee.impl;

import net.galaxy.rfbee.ReceivedMessage;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 8:23 PM
 */
public final class ReceivedMessageImpl implements ReceivedMessage {

    private final int src;
    private final int dst;
    private final ByteBuffer data;
    private final int rssi;
    private final int lqi;

    protected ReceivedMessageImpl(int src, int dst, ByteBuffer data, int rssi, int lqi) {
        this.src = src;
        this.dst = dst;
        this.data = data;
        this.rssi = rssi;
        this.lqi = lqi;
    }

    protected ReceivedMessageImpl(int src, int dst, ByteBuffer data) {
        this(src, dst, data, 0, 0);
    }

    protected ReceivedMessageImpl(ByteBuffer data) {
        this(-1, -1, data, 0, 0);
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDst() {
        return dst;
    }

    @Override
    public ByteBuffer getData() {
        return data;
    }

    @Override
    public int getRssi() {
        return rssi;
    }

    @Override
    public int getLqi() {
        return lqi;
    }
}
