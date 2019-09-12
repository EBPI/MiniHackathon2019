/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.visma.connect.hackathon;

import java.util.Objects;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

@DataType()
public class Meter {

    @Property()
    private String id;

    @Property()
    private String model;

    @Property()
    private String meterReadings;

    @Property()
    private String location;

    @Property()
    private String installDate;

    public String toJSONString() {
        return new JSONObject(this).toString();
    }

    public static Meter fromJSONString(String json) {
        JSONObject jsonObject = new JSONObject(json);
        String id = jsonObject.getString("id");
        String model = jsonObject.getString("model");
        String meterReadings = jsonObject.getString("meterReadings");
        String location = jsonObject.getString("location");
        String installDate = jsonObject.getString("installDate");

        Meter asset = new Meter();
        asset.setId(id);
        asset.setModel(model);
        asset.setMeterReadings(meterReadings);
        asset.setLocation(location);
        asset.setInstallDate(installDate);

        return asset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMeterReadings() {
        return meterReadings;
    }

    public void setMeterReadings(String meterReadings) {
        this.meterReadings = meterReadings;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Meter)) {
            return false;
        }
        Meter castOther = (Meter) other;
        return Objects.equals(id, castOther.id) && Objects.equals(model, castOther.model) && Objects.equals(meterReadings, castOther.meterReadings)
                && Objects.equals(location, castOther.location) && Objects.equals(installDate, castOther.installDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, meterReadings, location, installDate);
    }
}
