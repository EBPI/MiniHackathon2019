package com.visma.connect.hackathon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContractTaskTest {

    @Test
    public void testFromTOJSONString() throws Exception {
        ContractTask ct = new ContractTask();
        ct.setContractorId("abc");
        ct.setCreationDate("2019-09-12 13:15:000");
        ct.setDescription("SomeDescription");
        ct.setDueDate("2019-10-12 00:00:00");
        ct.setId("task001");
        ct.setMeterId("meter002");
        ct.setStatus("TODO");
        ct.setType("REPLACE");
        ct.setVerifierId("Jan Jansen");

        String jsonString = ct.toJSONString();
        System.out.println(jsonString);
        ContractTask contractTask = ContractTask.fromJSONString(jsonString);

        Assertions.assertThat(contractTask).isEqualTo(ct);
    }

}
