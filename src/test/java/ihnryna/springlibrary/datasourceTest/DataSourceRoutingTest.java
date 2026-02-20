package ihnryna.springlibrary.datasourceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimeZone;

@SpringBootTest
public class DataSourceRoutingTest {

    @Autowired
    private RoutingTestService service;

    @BeforeAll
    static void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void readOnlyTransactionShouldUseReplica() throws Exception {
        String name = service.readTest();
        Assertions.assertEquals("replica",name);
    }

    @Test
    void writeTransactionShouldUsePrimary() throws Exception {

        String name = service.writeTest();
        Assertions.assertEquals("primary", name);
    }

}
