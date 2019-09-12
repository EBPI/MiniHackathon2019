/*
 * SPDX-License-Identifier: Apache-2.0
 */
package com.visma.connect.hackathon;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract(name = "MeterContract",
        info = @Info(title = "Meter contract", description = "My Smart Contract", version = "0.0.1", license = @License(name = "Apache-2.0", url = ""),
                contact = @Contact(email = "smartcontract@example.com", name = "smartcontract", url = "http://smartcontract.me")))
@Default
public class MeterContract implements ContractInterface {
    private static final String ASSET_NAME = "METER";
    private static final String ASSET_PREFIX = ASSET_NAME + ":";

    public MeterContract() {

    }

    @Transaction()
    public boolean meterExists(Context ctx, String meterId) {
        byte[] buffer = ctx.getStub().getState(toKey(meterId));
        return buffer != null && buffer.length > 0;
    }

    private String toKey(String meterId) {
        return ASSET_PREFIX + meterId;
    }

    @Transaction()
    public void createMeter(Context ctx, String meterId, String installDate, String location, String meterReadings, String model) {
        boolean exists = meterExists(ctx, meterId);
        if (exists) {
            throw new RuntimeException("The asset " + meterId + " already exists");
        }
        save(ctx, meterId, installDate, location, meterReadings, model);
    }

    private void save(Context ctx, String meterId, String installDate, String location, String meterReadings, String model) {
        Meter m = new Meter();
        m.setId(meterId);
        m.setInstallDate(installDate);
        m.setLocation(location);
        m.setMeterReadings(meterReadings);
        m.setModel(model);

        ctx.getStub().putState(toKey(meterId), m.toJSONString().getBytes(UTF_8));
    }

    @Transaction()
    public Meter readMeter(Context ctx, String meterId) {
        boolean exists = meterExists(ctx, meterId);
        if (!exists) {
            throw new RuntimeException("The asset " + meterId + " does not exist");
        }

        Meter meter = Meter.fromJSONString(new String(ctx.getStub().getState(toKey(meterId)), UTF_8));
        return meter;
    }

    @Transaction()
    public void updateMeter(Context ctx, String meterId, String installDate, String location, String meterReadings, String model) {
        boolean exists = meterExists(ctx, meterId);
        if (!exists) {
            throw new RuntimeException("The asset " + meterId + " does not exist");
        }
        save(ctx, meterId, installDate, location, meterReadings, model);
    }

}
