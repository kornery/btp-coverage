/*
 * Copyright 2021 ICON Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package foundation.icon.btp.test;

import foundation.icon.btp.lib.BMVStatus;
import foundation.icon.btp.mock.MockBMV;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;

import java.math.BigInteger;
import java.util.function.Consumer;

public interface MockBMVIntegrationTest {

    MockBMV mockBMV = EVMIntegrationTest.deploy(MockBMV.class);

    static Consumer<TransactionReceipt> handleRelayMessageEvent(
            Consumer<MockBMV.HandleRelayMessageEventResponse> consumer) {
        return EVMIntegrationTest.eventLogChecker(
                mockBMV.getContractAddress(),
                MockBMV::getHandleRelayMessageEvents,
                consumer);
    }

    static BMVStatus getStatus() {
        try {
            Tuple2<BigInteger, byte[]> tuple = mockBMV.getStatus().send();
            return new BMVStatus(tuple.component1().longValue(), tuple.component2());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
