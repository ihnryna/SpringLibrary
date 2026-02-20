package ihnryna.springlibrary.datasourceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimeZone;

@SpringBootTest
@Transactional
public class ReplicationTest {

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDs;

    @Autowired
    @Qualifier("replicaDataSource")
    private DataSource replicaDs;

    @BeforeAll
    static void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void writeToFirstChangesDataOnSecond() throws Exception {
        try (Connection primary = primaryDs.getConnection();
             Connection replica = replicaDs.getConnection()) {

            Statement stmt1 = primary.createStatement();
            stmt1.executeUpdate("INSERT INTO author_stats (author_name, country, active, books_count, earliest_publish_year) VALUES ('Ira H', 'Ukraine', true, 1, 2005)");

            Thread.sleep(1000);

            Statement stmt2 = replica.createStatement();
            ResultSet rs = stmt2.executeQuery("SELECT author_name FROM author_stats WHERE author_name='Ira H'");

            Assertions.assertTrue(rs.next());
        }
    }
}
