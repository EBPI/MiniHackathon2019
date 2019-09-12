/*
 * SPDX-License-Identifier: Apache License 2.0
 */

package com.visma.connect.hackathon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import org.assertj.core.api.Assertions;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public final class ContractTaskContractTest {

    @Nested
    class AssetExists {
        @Test
        public void noProperAsset() {

            ContractTaskContract contract = new ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("CONTRACT_TASK:10001")).thenReturn(new byte[] {});
            boolean result = contract.contractTaskExists(ctx, "10001");

            assertFalse(result);
        }

        @Test
        public void assetExists() {

            ContractTaskContract contract = new ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("CONTRACT_TASK:10001")).thenReturn(new byte[] { 42 });
            boolean result = contract.contractTaskExists(ctx, "10001");

            assertTrue(result);

        }

        @Test
        public void noKey() {
            ContractTaskContract contract = new ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("CONTRACT_TASK:10002")).thenReturn(null);
            boolean result = contract.contractTaskExists(ctx, "10002");

            assertFalse(result);

        }

    }

    @Nested
    class AssetCreates {

        @Test
        public void newAssetCreate() {
            ContractTaskContract contract = new ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            ArgumentCaptor<String> key = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<byte[]> jsonString = ArgumentCaptor.forClass(byte[].class);
            Mockito.doNothing().when(stub).putState(key.capture(), jsonString.capture());

            String contractTaskId = "10001";
            String creationDate = "2019-09-12 13:52:00";
            String description = "TheContractTask";
            String dueDate = "2019-10-12 00:00:00";
            String contractorId = "TheUltimateContractor";
            String meterId = "Meter00003";
            String status = "TODO";
            String type = "REPLACE";
            String verifierId = "SomeOne";
            contract.createContractTask(ctx, contractTaskId, creationDate, description, dueDate, contractorId, meterId, status, type, verifierId);

            Assertions.assertThat(key.getValue()).isEqualTo("CONTRACT_TASK:10001");
            ContractTask contractTask = ContractTask.fromJSONString(new String(jsonString.getValue()));
            Assertions.assertThat(contractTask.getContractorId()).isEqualTo(contractorId);
            Assertions.assertThat(contractTask.getCreationDate()).isEqualTo(creationDate);
            Assertions.assertThat(contractTask.getDescription()).isEqualTo(description);
            Assertions.assertThat(contractTask.getDueDate()).isEqualTo(dueDate);
            Assertions.assertThat(contractTask.getId()).isEqualTo(contractTaskId);
            Assertions.assertThat(contractTask.getMeterId()).isEqualTo(meterId);
            Assertions.assertThat(contractTask.getStatus()).isEqualTo(status);
            Assertions.assertThat(contractTask.getType()).isEqualTo(type);
            Assertions.assertThat(contractTask.getVerifierId()).isEqualTo(verifierId);

        }

        @Test
        public void alreadyExists() {
            ContractTaskContract contract = new ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("CONTRACT_TASK:10002")).thenReturn(new byte[] { 42 });

            String contractTaskId = "10002";
            String creationDate = "2019-09-12 13:52:00";
            String description = "TheContractTask";
            String dueDate = "2019-10-12 00:00:00";
            String contractorId = "TheUltimateContractor";
            String meterId = "Meter00003";
            String status = "TODO";
            String type = "REPLACE";
            String verifierId = "SomeOne";

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createContractTask(ctx, contractTaskId, creationDate, description, dueDate, contractorId, meterId, status, type, verifierId);
                ;
            });

            assertEquals(thrown.getMessage(), "The asset 10002 already exists");

        }

    }

    @Test
    public void assetRead() {
        ContractTaskContract contract = new ContractTaskContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        ContractTask asset = new ContractTask();
        asset.setId("Valuable");

        String json = asset.toJSONString();
        when(stub.getState("CONTRACT_TASK:10001")).thenReturn(json.getBytes(StandardCharsets.UTF_8));

        ContractTask returnedAsset = contract.readContractTask(ctx, "10001");
        assertEquals(returnedAsset.getId(), asset.getId());
    }

    @Nested
    class AssetUpdates {
        @Test
        public void updateExisting() {
            ContractTaskContract contract = new ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getState("CONTRACT_TASK:10003")).thenReturn(new byte[] { 42 });

            ArgumentCaptor<String> key = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<byte[]> jsonString = ArgumentCaptor.forClass(byte[].class);
            Mockito.doNothing().when(stub).putState(key.capture(), jsonString.capture());

            String contractTaskId = "10003";
            String creationDate = "2019-09-12 13:52:00";
            String description = "TheContractTask";
            String dueDate = "2019-10-12 00:00:00";
            String contractorId = "TheUltimateContractor";
            String meterId = "Meter00003";
            String status = "TODO";
            String type = "REPLACE";
            String verifierId = "SomeOne";

            contract.updateContractTask(ctx, contractTaskId, creationDate, description, dueDate, contractorId, meterId, status, type, verifierId);

            Assertions.assertThat(key.getValue()).isEqualTo("CONTRACT_TASK:10003");
            ContractTask contractTask = ContractTask.fromJSONString(new String(jsonString.getValue()));
            Assertions.assertThat(contractTask.getContractorId()).isEqualTo(contractorId);
            Assertions.assertThat(contractTask.getCreationDate()).isEqualTo(creationDate);
            Assertions.assertThat(contractTask.getDescription()).isEqualTo(description);
            Assertions.assertThat(contractTask.getDueDate()).isEqualTo(dueDate);
            Assertions.assertThat(contractTask.getId()).isEqualTo(contractTaskId);
            Assertions.assertThat(contractTask.getMeterId()).isEqualTo(meterId);
            Assertions.assertThat(contractTask.getStatus()).isEqualTo(status);
            Assertions.assertThat(contractTask.getType()).isEqualTo(type);
            Assertions.assertThat(contractTask.getVerifierId()).isEqualTo(verifierId);

        }

        @Test
        public void updateMissing() {
            ContractTaskContract contract = new ContractTaskContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("CONTRACT_TASK10004")).thenReturn(null);

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.updateContractTask(ctx, "10004", "TheContractTask", "2019-09-12 13:52:00", "myFirstContract", "2019-10-12 00:00:00", "meter100",
                        "TODO", "PowerMeter", "Me Myself");
            });

            assertEquals(thrown.getMessage(), "The asset 10004 does not exist");
        }

    }

}
