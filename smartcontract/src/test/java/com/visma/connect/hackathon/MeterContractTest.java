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

public final class MeterContractTest {

    @Nested
    class AssetExists {
        @Test
        public void noProperAsset() {

            MeterContract contract = new MeterContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("METER:10001")).thenReturn(new byte[] {});
            boolean result = contract.meterExists(ctx, "10001");

            assertFalse(result);
        }

        @Test
        public void assetExists() {

            MeterContract contract = new MeterContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("METER:10001")).thenReturn(new byte[] { 42 });
            boolean result = contract.meterExists(ctx, "10001");

            assertTrue(result);

        }

        @Test
        public void noKey() {
            MeterContract contract = new MeterContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("METER:10002")).thenReturn(null);
            boolean result = contract.meterExists(ctx, "10002");

            assertFalse(result);

        }

    }

    @Nested
    class AssetCreates {

        @Test
        public void newAssetCreate() {
            MeterContract contract = new MeterContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            ArgumentCaptor<String> key = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<byte[]> jsonString = ArgumentCaptor.forClass(byte[].class);
            Mockito.doNothing().when(stub).putState(key.capture(), jsonString.capture());

            String meterId = "10001";
            String installDate = "2019-09-12 13:52:00";
            String location = "somePlace";
            String meterReadings = "102";
            String model = "SmartMeter";
            contract.createMeter(ctx, meterId, installDate, location, meterReadings, model);

            Assertions.assertThat(key.getValue()).isEqualTo("METER:10001");
            Meter meter = Meter.fromJSONString(new String(jsonString.getValue()));
            Assertions.assertThat(meter.getId()).isEqualTo(meterId);
            Assertions.assertThat(meter.getInstallDate()).isEqualTo(installDate);
            Assertions.assertThat(meter.getLocation()).isEqualTo(location);
            Assertions.assertThat(meter.getMeterReadings()).isEqualTo(meterReadings);
            Assertions.assertThat(meter.getModel()).isEqualTo(model);
        }

        @Test
        public void alreadyExists() {
            MeterContract contract = new MeterContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("METER:10002")).thenReturn(new byte[] { 42 });

            String meterId = "10002";
            String installDate = "2019-09-12 13:52:00";
            String location = "somePlace";
            String meterReadings = "102";
            String model = "SmartMeter";

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createMeter(ctx, meterId, installDate, location, meterReadings, model);
                ;
            });

            assertEquals(thrown.getMessage(), "The asset 10002 already exists");

        }

    }

    @Test
    public void assetRead() {
        MeterContract contract = new MeterContract();

        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        Meter asset = new Meter();
        asset.setId("Valuable");

        String json = asset.toJSONString();
        when(stub.getState("METER:10001")).thenReturn(json.getBytes(StandardCharsets.UTF_8));

        Meter returnedAsset = contract.readMeter(ctx, "10001");
        assertEquals(returnedAsset.getId(), asset.getId());
    }

    @Nested
    class AssetUpdates {
        @Test
        public void updateExisting() {
            MeterContract contract = new MeterContract();

            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getState("METER:10003")).thenReturn(new byte[] { 42 });

            ArgumentCaptor<String> key = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<byte[]> jsonString = ArgumentCaptor.forClass(byte[].class);
            Mockito.doNothing().when(stub).putState(key.capture(), jsonString.capture());
            String meterId = "10003";
            String installDate = "2019-09-12 13:52:00";
            String location = "somePlace";
            String meterReadings = "102";
            String model = "SmartMeter";
            contract.updateMeter(ctx, meterId, installDate, location, meterReadings, model);

            Assertions.assertThat(key.getValue()).isEqualTo("METER:10003");
            Meter meter = Meter.fromJSONString(new String(jsonString.getValue()));
            Assertions.assertThat(meter.getId()).isEqualTo(meterId);
            Assertions.assertThat(meter.getInstallDate()).isEqualTo(installDate);
            Assertions.assertThat(meter.getLocation()).isEqualTo(location);
            Assertions.assertThat(meter.getMeterReadings()).isEqualTo(meterReadings);
            Assertions.assertThat(meter.getModel()).isEqualTo(model);

        }

        @Test
        public void updateMissing() {
            MeterContract contract = new MeterContract();

            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("METER:10004")).thenReturn(null);
            String meterId = "10004";
            String installDate = "2019-09-12 13:52:00";
            String location = "somePlace";
            String meterReadings = "102";
            String model = "SmartMeter";

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.updateMeter(ctx, meterId, installDate, location, meterReadings, model);
            });

            assertEquals(thrown.getMessage(), "The asset 10004 does not exist");
        }

    }

}
