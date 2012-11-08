/*
 * Project: rfbee-java, file: ReceivedMessageFactory.java
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

import com.google.common.io.ByteStreams;
import net.galaxy.rfbee.ReceivedMessage;
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
        return new ReceivedMessageImpl(src, dst, buff, rssi, lqi);
    }
}
