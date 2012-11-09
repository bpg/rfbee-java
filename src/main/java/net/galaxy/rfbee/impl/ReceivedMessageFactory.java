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

import com.google.common.io.ByteStreams;
import net.galaxy.rfbee.ReceivedMessage;
import net.galaxy.rfbee.SignalQuality;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 07/11/12 9:19 PM
 */
public class ReceivedMessageFactory {

    private static final Logger logger = LoggerFactory.getLogger(ReceivedMessageFactory.class.getSimpleName());

    public static ReceivedMessage getFromStream(InputStream stream) throws IOException {
        int payloadLen = stream.read();
        logger.trace("received: payload len: {}", payloadLen);
        int src = stream.read();
        logger.trace("received: src: {}", src);
        int dst = stream.read();
        logger.trace("received: dst: {}", dst);
        byte[] buff = new byte[payloadLen];
        ByteStreams.readFully(stream, buff);
        logger.trace("received: payload data: {}", Hex.encodeHexString(buff));
        int rssi = stream.read();
        logger.trace("received: rssi: {}", rssi);
        int lqi = stream.read();
        logger.trace("received: lqi: {}", lqi);
        return new ReceivedMessageImpl(src, dst, buff, new SignalQuality(rssi, lqi));
    }

    public static ReceivedMessage wrap(ReceivedMessage msg, byte[] newData) {
        return new ReceivedMessageImpl(msg.getSrc(), msg.getDst(), newData, msg.getSignalQuality());
    }
}
