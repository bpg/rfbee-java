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

import net.galaxy.rfbee.ReceivedMessage;
import net.galaxy.rfbee.SignalQuality;
import org.apache.commons.codec.binary.Hex;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 8:23 PM
 */
public final class ReceivedMessageImpl implements ReceivedMessage {

    private final int src;
    private final int dst;
    private final byte[] payload;
    private final SignalQuality signalQuality;

    protected ReceivedMessageImpl(int src, int dst, byte[] payload, SignalQuality signalQuality) {
        this.src = src;
        this.dst = dst;
        this.payload = payload;
        this.signalQuality = signalQuality;
    }

    protected ReceivedMessageImpl(int src, int dst, byte[] payload) {
        this(src, dst, payload, SignalQuality.NA);
    }

    protected ReceivedMessageImpl(byte[] payload) {
        this(-1, -1, payload);
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
    public byte[] getPayload() {
        return payload;
    }

    @Override
    public SignalQuality getSignalQuality() {
        return signalQuality;
    }

    @Override
    public String toString() {
        return "ReceivedMessageImpl{" +
                "src=" + src +
                ", dst=" + dst +
                ", payload=" + Hex.encodeHexString(payload) +
                ", signalQuality=" + signalQuality +
                '}';
    }
}
