/*
 * Project: rfbee-java, file: CommandMode.java
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

package net.galaxy.rfbee;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/07/12 7:10 PM
 */
public interface CommandMode {

    static final String OK = "ok\n\r";

    static enum AddressCheck {
        /**
         * No address check.
         * <p/>
         * This will disable address checking and all RFBee’s will receive all packets sent. Downside of this is
         * that any “private” communication between e.g. 2 and 3 will always show up at 1 and 4.
         */
        NONE,
        /**
         * Address check, no broadcast.
         * <p/>
         * Only point-to-point communication.
         */
        CHECK_NO_BROADCAST,
        /**
         * Address check and broadcast.
         * <p/>
         * This will enable address checking including broadcasts. This will enable private communication
         * between RFBee’s (e.g. packets between 2 and 3 will not show up at 1 and 4). The RFBee sending the broadcast
         * must set the destination address to 0. Packets with destination address 0 will be received by all four RFBee’s.
         */
        CHECK_BROADCAST
    }

    /**
     * Sets address check mode.
     *
     * @param addressCheck address check mode, 0 - NONE by default
     */
    void setAddressCheck(AddressCheck addressCheck);

    /**
     * Sets own address.
     *
     * @param address 0-255, 0 by default (i.e. any address).
     */
    void setAddress(int address);

    /**
     * Sets address of destination.
     *
     * @param address 0-255, 0 by default (i.e. broadcast).
     */
    void setAddressDst(int address);


    /**
     * Defines transmission power in dBm.
     */
    static enum RadioPower {
        /**
         * -30bBm
         */
        Lm30,
        /**
         * -20dBm
         */
        Lm20,
        /**
         * -15dBm
         */
        Lm15,
        /**
         * -10dBm
         */
        Lm10,
        /**
         * 0dBm
         */
        L000,
        /**
         * 5dBm,
         */
        Lp05,
        /**
         * 7dBm,
         */
        Lp07,
        /**
         * 10dBm,
         */
        Lp10
    }

    /**
     * Sets transmission power.
     *
     * @param power Lp10 (+10dBm) by default.
     */
    void setRadioPower(RadioPower power);

    /**
     * Defines radio config
     */
    static enum RadioConfig {
        /**
         * 915MHz, 76,8Kbps, 2-FSK
         */
        MHZ_915_MODE1,
        /**
         * 915MHz, 4.8Kbps, GFSK, sensitivity
         */
        MHZ_915_MODE2,
        /**
         * 915MHz, 4.8Kbps, GFSK, low current
         */
        MHZ_950_MODE3,
        /**
         * 915MHz, 76,8Kbps, 2-FSK
         */
        MHZ_868_MODE1,
        /**
         * 868MHz, 4.8Kbps, GFSK, sensitivity
         */
        MHZ_868_MODE2,
        /**
         * 868MHz, 4.8Kbps, GFSK, low current
         */
        MHZ_868_MODE3
    }

    /**
     * Sets radio config.
     *
     * @param radioConfig MHZ_915_MODE1 (915MHz, 76,8Kbps, 2-FSK) by default.
     */
    void setRadioConfig(RadioConfig radioConfig);


    /**
     * UART baud rate, in bps.
     */
    static enum SerialBaudRate {
        BPS_9600,
        BPS_19200,
        BPS_38400,
        BPS_115200
    }

    /**
     * Sets UART baud rate.
     *
     * @param serialBaudRate, 9600 by default.
     */
    void setSerialBaudRate(SerialBaudRate serialBaudRate);

    /**
     * Sets threshold in bytes that will trigger RFBee to start transmission.
     *
     * @param threshold 1-32, 1 by default.
     */
    void setSerialThreshold(byte threshold);


    static enum SerialOutputFormat {
        /**
         * Payload only.
         */
        PAYLOAD,
        /**
         * <ul>
         * <li>1 byte - source,</li>
         * <li>1 byte - destination,</li>
         * <li>N bytes - payload.</li>
         * </ul>
         */
        SRC_DST_PAYLOAD,
        /**
         * <ul>
         * <li>1 byte - payload length N,</li>
         * <li>1 byte - source,</li>
         * <li>1 byte - destination,</li>
         * <li>N bytes - payload.</li>
         * <li>1 byte - RSSI (received signal strength indicator), signed byte, dBm</li>
         * <li>1 byte - LQI (link quality indicator),</li>
         * </ul>
         */
        EXTENDED,
        /**
         * Same as EXTENDED, but all bytes except Payload returns as decimal, and all fields separated by comma's.
         */
        EXTENDED_CSV
    }

    /**
     * Sets serial output format.
     *
     * @param serialOutputFormat PAYLOAD by default.
     */
    void setSerialOutputFormat(SerialOutputFormat serialOutputFormat) throws IOException;

    static enum TransmissionMode {
        TRANSCEIVE,
        TRANSMIT_ONLY,
        RECEIVE_ONLY,
        /**
         * Using wake-on-radio.
         */
        LOW_POWER
    }

    /**
     * Sets transmission mode
     *
     * @param transmissionMode TRANSCEIVE by default.
     */
    void setTransmissionMode(TransmissionMode transmissionMode);

    String getFirmwareVersion() throws IOException;

    String getHardwareVersion() throws IOException;

    void resetToDefault();
}
