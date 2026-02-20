package ihnryna.springlibrary.datasourceTest;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DBSIdeMaxConnectionsTest {


    private static final String JDBC_URL = "jdbc:postgresql://localhost:5433/mydb?ApplicationName=primary";
    private static final String USER = "user";
    private static final String PASS = "pass";

    @Test
    void maxConnectionsEnforcedAfterDockerRestart() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        try (Connection admin = DriverManager.getConnection(JDBC_URL, USER, PASS);
             Statement s = admin.createStatement()) {
            s.execute("ALTER SYSTEM SET max_connections = 4");
        }

        ProcessBuilder pb = new ProcessBuilder("docker", "restart", "pg_primary");
        pb.inheritIO();
        Process p = pb.start();
        p.waitFor(100, TimeUnit.SECONDS);

        waitForPostgres(JDBC_URL, USER, PASS, 15);

        try (Connection admin = DriverManager.getConnection(JDBC_URL, USER, PASS);
             Statement s = admin.createStatement();
             ResultSet rs = s.executeQuery("SHOW max_connections")) {
            rs.next();
            int max = rs.getInt(1);
            Assertions.assertEquals(4, max, "max_connections повинен бути 2 після рестарту");
        }

        HikariDataSource ds1 = new HikariDataSource();
        ds1.setJdbcUrl(JDBC_URL);
        ds1.setUsername(USER);
        ds1.setPassword(PASS);
        ds1.setMaximumPoolSize(1);

        Connection c1 = ds1.getConnection();

        HikariDataSource ds12 = new HikariDataSource();
        ds12.setJdbcUrl(JDBC_URL);
        ds12.setUsername(USER);
        ds12.setPassword(PASS);
        ds12.setMaximumPoolSize(1);

        Connection c12 = ds12.getConnection();

        HikariDataSource ds13 = new HikariDataSource();
        ds13.setJdbcUrl(JDBC_URL);
        ds13.setUsername(USER);
        ds13.setPassword(PASS);
        ds13.setMaximumPoolSize(1);

        Connection c13 = ds13.getConnection();

        HikariDataSource ds131 = new HikariDataSource();
        ds131.setJdbcUrl(JDBC_URL);
        ds131.setUsername(USER);
        ds131.setPassword(PASS);
        ds131.setMaximumPoolSize(1);

        Connection c131 = ds131.getConnection();

        HikariDataSource ds2 = new HikariDataSource();
        ds2.setJdbcUrl(JDBC_URL);
        ds2.setUsername(USER);
        ds2.setPassword(PASS);
        ds2.setMaximumPoolSize(1);
        ds2.setConnectionTimeout(1000);

        assertThrows(SQLException.class, ds2::getConnection);

        c1.close();
        ds1.close();
        ds2.close();
        ds12.close();ds13.close();

        try (Connection admin = DriverManager.getConnection(JDBC_URL, USER, PASS);
             Statement s = admin.createStatement()) {
            s.execute("ALTER SYSTEM SET max_connections = 100");
        }
        pb.start().waitFor(30, TimeUnit.SECONDS);
    }

    private void waitForPostgres(String jdbcUrl, String user, String pass, int timeoutSeconds) throws InterruptedException {
        long end = System.currentTimeMillis() + timeoutSeconds * 1000L;
        while (System.currentTimeMillis() < end) {
            try (Connection c = DriverManager.getConnection(jdbcUrl, user, pass)) {
                return;
            } catch (SQLException ignored) {
                Thread.sleep(500);
            }
        }
        throw new RuntimeException("PostgreSQL не запустився за " + timeoutSeconds + " секунд");
    }
}