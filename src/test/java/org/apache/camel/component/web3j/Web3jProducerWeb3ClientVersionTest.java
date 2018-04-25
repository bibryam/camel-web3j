/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.web3j;

import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;

import static org.apache.camel.component.web3j.Web3jConstants.*;
import static org.mockito.ArgumentMatchers.any;

public class Web3jProducerWeb3ClientVersionTest extends Web3jTestSupport {

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Override
    public boolean isUseAdviceWith() {
        return false;
    }

    @Test
    public void clientVersionTest() throws Exception {
        Request request = Mockito.mock(Request.class);
        Web3ClientVersion response = Mockito.mock(Web3ClientVersion.class);

        Mockito.when(mockWeb3j.web3ClientVersion()).thenReturn(request);
        Mockito.when(request.send()).thenReturn(response);
        Mockito.when(response.getWeb3ClientVersion()).thenReturn("Geth-123");

        Exchange exchange = createExchangeWithBodyAndHeader(null, OPERATION, WEB3_CLIENT_VERSION);
        template.send(exchange);
        String body = exchange.getIn().getBody(String.class);
        assertTrue(body.startsWith("Geth"));
    }

    @Test
    public void netVersionTest() throws Exception {
        Request request = Mockito.mock(Request.class);
        NetVersion response = Mockito.mock(NetVersion.class);

        Mockito.when(mockWeb3j.netVersion()).thenReturn(request);
        Mockito.when(request.send()).thenReturn(response);
        Mockito.when(response.getNetVersion()).thenReturn("Net-123");

        Exchange exchange = createExchangeWithBodyAndHeader(null, OPERATION, NET_VERSION);
        template.send(exchange);
        String body = exchange.getIn().getBody(String.class);
        assertTrue(body.startsWith("Net"));
    }

    @Test
    public void netWeb3Sha3Test() throws Exception {
        Request request = Mockito.mock(Request.class);
        Web3Sha3 response = Mockito.mock(Web3Sha3.class);

        Mockito.when(mockWeb3j.web3Sha3(any())).thenReturn(request);
        Mockito.when(request.send()).thenReturn(response);
        Mockito.when(response.getResult()).thenReturn("0x471");

        Exchange exchange = createExchangeWithBodyAndHeader(null, OPERATION, WEB3_SHA3);
        exchange.getIn().setBody("0x68");
        template.send(exchange);
        String body = exchange.getIn().getBody(String.class);
        assertTrue(body.equals("0x471"));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                        .to(getUrl() + OPERATION.toLowerCase() + "=" + WEB3_CLIENT_VERSION);
            }
        };
    }
}
