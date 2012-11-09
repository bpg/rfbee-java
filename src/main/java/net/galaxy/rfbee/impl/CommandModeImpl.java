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
