package ihnryna.springlibrary;

import ihnryna.springlibrary.model.AuthorStats;
import ihnryna.springlibrary.repository.AuthorStatsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class IndexTest {
    @Autowired
    private AuthorStatsRepository authorStatsRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldThrowExceptionWhenDuplicateAuthorNamesInserted() {

        authorStatsRepository.deleteAll();

        AuthorStats author1 = new AuthorStats();
        author1.setAuthorName("Iryna Hryshchenko");
        author1.setBooksCount(2);
        author1.setEarliestPublishYear(2018);

        AuthorStats author2 = new AuthorStats();
        author2.setAuthorName("Iryna Hryshchenko");
        author2.setBooksCount(3);
        author2.setEarliestPublishYear(2005);

        authorStatsRepository.saveAndFlush(author1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            authorStatsRepository.saveAndFlush(author2);
        });
    }

    @Test
    void shouldContainAuthorNameIdx() {

        String sql = """
            SELECT indexname
            FROM pg_indexes
            WHERE tablename = 'author_stats'
            AND indexname = 'author_name_idx'
        """;

        List<String> result = jdbcTemplate.queryForList(sql, String.class);

        assertFalse(result.isEmpty());
    }
}
