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

@Contract(name = "ContractTaskContract", info = @Info(title = "ContractTask contract", description = "My Smart Contract", version = "0.0.1", license = @License(name = "Apache-2.0", url = ""), contact = @Contact(email = "smartcontract@example.com", name = "smartcontract", url = "http://smartcontract.me")))
@Default
public class ContractTaskContract implements ContractInterface {
    private static final String ASSET_NAME = "CONTRACT_TASK";
    private static final String ASSET_PREFIX = ASSET_NAME + ":";

    public ContractTaskContract() {

    }

    @Transaction(submit = false)
    public boolean contractTaskExists(Context ctx, String contractTaskId) {
        byte[] buffer = ctx.getStub().getState(toKey(contractTaskId));
        return buffer != null && buffer.length > 0;
    }

    @Transaction()
    public void createContractTask(Context ctx, String contractTaskId, String creationDate, String description,
            String dueDate, String contractorId, String meterId, String status, String type, String verifierId) {
        boolean exists = contractTaskExists(ctx, contractTaskId);
        if (exists) {
            throw new RuntimeException("The asset " + contractTaskId + " already exists");
        }
        save(ctx, contractTaskId, creationDate, description, dueDate, contractorId, meterId, status, type, verifierId);
    }

    private void save(Context ctx, String contractTaskId, String creationDate, String description, String dueDate,
            String contractorId, String meterId, String status, String type, String verifierId) {
        ContractTask ct = new ContractTask();
        ct.setContractorId(contractorId);
        ct.setCreationDate(creationDate);
        ct.setDescription(description);
        ct.setDueDate(dueDate);
        ct.setId(contractTaskId);
        ct.setMeterId(meterId);
        ct.setStatus(status);
        ct.setType(type);
        ct.setVerifierId(verifierId);

        ctx.getStub().putState(toKey(contractTaskId), ct.toJSONString().getBytes(UTF_8));
    }

    @Transaction(submit = false)
    public ContractTask readContractTask(Context ctx, String contractTaskId) {
        boolean exists = contractTaskExists(ctx, contractTaskId);
        if (!exists) {
            throw new RuntimeException("The asset " + contractTaskId + " does not exist");
        }

        ContractTask ct = ContractTask.fromJSONString(new String(ctx.getStub().getState(toKey(contractTaskId)), UTF_8));
        return ct;
    }

    @Transaction()
    public void updateContractTask(Context ctx, String contractTaskId, String creationDate, String description,
            String dueDate, String contractorId, String meterId, String status, String type, String verifierId) {
        boolean exists = contractTaskExists(ctx, contractTaskId);
        if (!exists) {
            throw new RuntimeException("The asset " + contractTaskId + " does not exist");
        }
        save(ctx, contractTaskId, creationDate, description, dueDate, contractorId, meterId, status, type, verifierId);
    }

    private String toKey(String meterId) {
        return ASSET_PREFIX + meterId;
    }

}
