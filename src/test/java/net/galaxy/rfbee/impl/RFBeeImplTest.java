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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import net.galaxy.rfbee.RFBee;
import net.galaxy.rfbee.ReceiveCallback;
import net.galaxy.rfbee.ReceivedMessage;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Pavel Boldyrev
 * 02/11/12 8:54 PM
 */
public class RFBeeImplTest {

    @BeforeClass
    public static void setUp() throws Exception {
        //turn off logging
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.getLogger("root").setLevel(Level.OFF);
    }

    @Ignore
    public void testA() throws IOException, InterruptedException {
        RFBee bee = new RFBeeImpl(0x0a);
        bee.registerReceiveCallback(new ReceiveCallback() {
            @Override
            public void receive(ReceivedMessage message) {
                System.out.println(new String(message.getPayload()));
            }
        });
        Thread.sleep(60000);
        bee.close();
    }
}
