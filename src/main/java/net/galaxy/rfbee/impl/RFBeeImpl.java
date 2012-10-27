/*
 * Project: rfbee-java, file: RFBeeImpl.java
 * Copyright (C) 2012 Pavel Boldyrev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
