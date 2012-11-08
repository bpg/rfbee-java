/*
 * Project: rfbee-java, file: CommandModeImpl.java
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

import net.galaxy.rfbee.CommandMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 28/10/12 8:58 AM
 */
public class CommandModeImpl implements CommandMode {

    private static final Logger logger = LoggerFactory.getLogger(CommandModeImpl.class.getSimpleName());


    private final Connection connection;

    public CommandModeImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void setAddressCheck(AddressCheck addressCheck) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAddress(int address) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAddressDst(int address) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setRadioPower(RadioPower power) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setRadioConfig(RadioConfig radioConfig) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSerialBaudRate(SerialBaudRate serialBaudRate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSerialThreshold(byte threshold) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSerialOutputFormat(SerialOutputFormat serialOutputFormat) throws IOException {
        logger.debug("set serial output format: {}", serialOutputFormat);
        connection.sendCmd("ATOF" + serialOutputFormat.ordinal() + "\r", "ok\r\n");
    }

    @Override
    public void setTransmissionMode(TransmissionMode transmissionMode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getFirmwareVersion() throws IOException {
        return connection.sendCmd("ATFV\r").replace("ok\r\n", "").trim();
    }

    @Override
    public String getHardwareVersion() throws IOException {
        return connection.sendCmd("ATHV\r").replace("ok\r\n", "").trim();
    }

    @Override
    public void resetToDefault() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
