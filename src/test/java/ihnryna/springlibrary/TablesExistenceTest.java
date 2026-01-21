package ihnryna.springlibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class TablesExistenceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Test
    void testJpaCreatesLibraryItemTable() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name='library_item'",
                Integer.class
        );
        assertEquals(1, count);
    }

    @Test
    void testBookAttributesPresentInLibraryItemTable() {
        Integer isbnCol = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.columns WHERE table_name='library_item' AND column_name='isbn'",
                Integer.class
        );
        assertEquals(1, isbnCol);
    }

    @Test
    void testMagazineAttributesPresentInLibraryItemTable() {
        Integer issueNumberCol = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.columns WHERE table_name='library_item' AND column_name='issue_number'",
                Integer.class
        );
        assertEquals(1, issueNumberCol);
    }

}
