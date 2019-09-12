/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.visma.connect.hackathon;

import java.util.Objects;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

@DataType()
public class ContractTask {

    @Property()
    private String id;

    @Property()
    private String description;

    @Property()
    private String creationDate;

    @Property()
    private String dueDate;

    @Property()
    private String type;

    @Property()
    private String status;

    @Property()
    private String contractorId;

    @Property()
    private String verifierId;

    @Property()
    private String meterId;

    public ContractTask() {
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getVerifierId() {
        return verifierId;
    }

    public void setVerifierId(String verifierId) {
        this.verifierId = verifierId;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getDescription() {
        return description;
    }

    public String toJSONString() {
        return new JSONObject(this).toString();
    }

    public static ContractTask fromJSONString(String json) {
        JSONObject jsonObject = new JSONObject(json);
        String id = jsonObject.getString("id");
        String description = jsonObject.getString("description");
        String creationDate = jsonObject.getString("creationDate");
        String dueDate = jsonObject.getString("dueDate");
        String type = jsonObject.getString("type");
        String status = jsonObject.getString("status");
        String contractorId = jsonObject.getString("contractorId");
        String verifierId = jsonObject.getString("verifierId");
        String meterId = jsonObject.getString("meterId");

        ContractTask asset = new ContractTask();
        asset.setId(id);
        asset.setDescription(description);
        asset.setCreationDate(creationDate);
        asset.setDueDate(dueDate);
        asset.setType(type);
        asset.setStatus(status);
        asset.setContractorId(contractorId);
        asset.setVerifierId(verifierId);
        asset.setMeterId(meterId);
        return asset;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ContractTask)) {
            return false;
        }
        ContractTask castOther = (ContractTask) other;
        return Objects.equals(id, castOther.id) && Objects.equals(description, castOther.description) && Objects.equals(creationDate, castOther.creationDate)
                && Objects.equals(dueDate, castOther.dueDate) && Objects.equals(type, castOther.type) && Objects.equals(status, castOther.status)
                && Objects.equals(contractorId, castOther.contractorId) && Objects.equals(verifierId, castOther.verifierId)
                && Objects.equals(meterId, castOther.meterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, creationDate, dueDate, type, status, contractorId, verifierId, meterId);
    }
}
