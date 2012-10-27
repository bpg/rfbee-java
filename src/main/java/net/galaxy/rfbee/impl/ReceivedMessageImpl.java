/*
 * Project: rfbee-java, file: ReceivedMessageImpl.java
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
