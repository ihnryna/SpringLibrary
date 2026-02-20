package ihnryna.springlibrary.datasourceTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLTransientConnectionException;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class HikariPoolTest {

    @Autowired
    private DataSource dataSource;

    @BeforeAll
    static void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void whenPoolExhausted_thenNewConnectionsFail() throws Exception {

        Connection c1 = dataSource.getConnection();
        c1.createStatement().execute("SELECT 1");
        Connection c2 = dataSource.getConnection();
        c2.createStatement().execute("SELECT 1");
        Connection c3 = dataSource.getConnection();
        c3.createStatement().execute("SELECT 1");
        Connection c4 = dataSource.getConnection();
        c4.createStatement().execute("SELECT 1");
        Connection c5 = dataSource.getConnection();
        c5.createStatement().execute("SELECT 1");

        assertThrows(SQLTransientConnectionException.class, () -> {
            Connection c = dataSource.getConnection();
            c.createStatement().execute("SELECT 1");
        });

        c1.close();
        c2.close();
        c3.close();
        c4.close();
        c5.close();
    }
}
