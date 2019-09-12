/*
 * SPDX-License-Identifier: Apache License 2.0
 */

package com.visma.connect.hackathon;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.visma.connect.hackathon.ContractTask;
import com.visma.connect.hackathon.ContractTaskContract;
import java.nio.charset.StandardCharsets;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public final class ContractTaskContractTest {

    @Nested
    class AssetExists {
        @Test
        public void noProperAsset() {

            ContractTaskContract contract = new  ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10001")).thenReturn(new byte[] {});
            boolean result = contract.contractTaskExists(ctx,"10001");

            assertFalse(result);
        }

        @Test
        public void assetExists() {

            ContractTaskContract contract = new  ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10001")).thenReturn(new byte[] {42});
            boolean result = contract.contractTaskExists(ctx,"10001");

            assertTrue(result);

        }

        @Test
        public void noKey() {
            ContractTaskContract contract = new  ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10002")).thenReturn(null);
            boolean result = contract.contractTaskExists(ctx,"10002");

            assertFalse(result);

        }

    }

    @Nested
    class AssetCreates {

        @Test
        public void newAssetCreate() {
            ContractTaskContract contract = new  ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            String json = "{\"value\":\"TheContractTask\"}";

            contract.createContractTask(ctx, "10001", "TheContractTask");

            verify(stub).putState("10001", json.getBytes(UTF_8));
        }

        @Test
        public void alreadyExists() {
            ContractTaskContract contract = new  ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10002")).thenReturn(new byte[] { 42 });

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createContractTask(ctx, "10002", "TheContractTask");
            });

            assertEquals(thrown.getMessage(), "The asset 10002 already exists");

        }

    }

    @Test
    public void assetRead() {
        ContractTaskContract contract = new  ContractTaskContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        ContractTask asset = new  ContractTask();
        asset.setId("Valuable");

        String json = asset.toJSONString();
        when(stub.getState("10001")).thenReturn(json.getBytes(StandardCharsets.UTF_8));

        ContractTask returnedAsset = contract.readContractTask(ctx, "10001");
        assertEquals(returnedAsset.getId(), asset.getId());
    }

    @Nested
    class AssetUpdates {
        @Test
        public void updateExisting() {
            ContractTaskContract contract = new  ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getState("10001")).thenReturn(new byte[] { 42 });

            contract.updateContractTask(ctx, "10001", "updates");

            String json = "{\"value\":\"updates\"}";
            verify(stub).putState("10001", json.getBytes(UTF_8));
        }

        @Test
        public void updateMissing() {
            ContractTaskContract contract = new  ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("10001")).thenReturn(null);

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.updateContractTask(ctx, "10001", "TheContractTask");
            });

            assertEquals(thrown.getMessage(), "The asset 10001 does not exist");
        }

    }

    @Test
    public void assetDelete() {
        ContractTaskContract contract = new  ContractTaskContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub.getState("10001")).thenReturn(null);

        Exception thrown = assertThrows(RuntimeException.class, () -> {
            contract.deleteContractTask(ctx, "10001");
        });

        assertEquals(thrown.getMessage(), "The asset 10001 does not exist");
    }

}
