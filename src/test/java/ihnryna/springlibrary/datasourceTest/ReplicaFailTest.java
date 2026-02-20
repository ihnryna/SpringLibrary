package ihnryna.springlibrary.datasourceTest;

import ihnryna.springlibrary.datasourceTest.RoutingTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimeZone;

@SpringBootTest(
        properties = {
                "replica.datasource.url=jdbc:postgresql://localhost:5999/mydb?ApplicationName=replica"
        }
)
public class ReplicaFailTest {

    @Autowired
    private RoutingTestService service;

    @BeforeAll
    static void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void readOnlyShouldFallbackToPrimaryIfReplicaDown() {
        String dbName = service.readTest();

        Assertions.assertEquals(
                "primary",
                dbName,
                "Replica недоступна, повинен використовувати primary"
        );
    }
}
