package ihnryna.springlibrary.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibraryItemJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public LibraryItemJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findAllBookTitles() {
        return jdbcTemplate.query(
                """
                SELECT li.title
                FROM library_item li
                JOIN book b ON li.id = b.id
                """,
                (rs, rowNum) -> rs.getString("title")
        );
    }

    public int countAvailableItems() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM library_item WHERE available = true",
                Integer.class
        );
    }

    public int insertLibraryItem(String title, int year, boolean available) {
        return jdbcTemplate.update(
                "INSERT INTO library_item (title, published_year, available) VALUES (?, ?, ?)",
                title, year, available
        );
    }
}
