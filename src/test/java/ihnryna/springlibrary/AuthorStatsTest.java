package ihnryna.springlibrary;

import ihnryna.springlibrary.model.AuthorStats;
import ihnryna.springlibrary.repository.AuthorStatsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@Testcontainers
@ActiveProfiles("test-flyway")
class AuthorStatsTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private AuthorStatsRepository repository;

    @Test
    void shouldLoadDataFromFinalMigratedTable() {
        List<AuthorStats> summaries = repository.findAll();

        assertThat(summaries).isNotEmpty();

        AuthorStats summary = summaries.getFirst();

        assertThat(summary.getAuthorName()).isNotBlank();
        assertThat(summary.getBooksCount()).isGreaterThan(0);
        assertThat(summary.getEarliestPublishYear()).isGreaterThan(1900);
    }

    @Test
    void shouldContainJoshuaBloch() {
        boolean exists = repository.findAll().stream()
                .anyMatch(a -> a.getAuthorName().equals("Joshua Bloch"));

        assertThat(exists).isTrue();
    }
}
