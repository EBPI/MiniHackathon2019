package com.visma.connect.hackathon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MeterTest {

    @Test
    public void testToFromJSONString() throws Exception {
        Meter m = new Meter();
        m.setId("001");
        m.setInstallDate("2019-09-12 12:34:22");
        m.setLocation("myPlace");
        m.setMeterReadings("123");
        m.setModel("Slim");

        String jsonString = m.toJSONString();
        System.out.println(jsonString);
        Meter meter = Meter.fromJSONString(jsonString);

        Assertions.assertThat(meter).isEqualTo(m);
    }

}
