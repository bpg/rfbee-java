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

package net.galaxy.rfbee;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 08/11/12 9:36 PM
 */
public class SignalQuality {

    public static final SignalQuality NA = new SignalQuality(-1, -1);

    private final int rssi;
    private final int lqi;

    public SignalQuality(int rssi, int lqi) {
        this.rssi = rssi;
        this.lqi = lqi;
    }

    public int getRssi() {
        return rssi;
    }

    public int getLqi() {
        return lqi;
    }

    @Override
    public String toString() {
        return "{" + rssi + ":" + lqi + "}";
    }
}
