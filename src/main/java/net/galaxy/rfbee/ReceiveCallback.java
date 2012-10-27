/*
 * Project: rfbee-java, file: ReceiveCallback.java
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
