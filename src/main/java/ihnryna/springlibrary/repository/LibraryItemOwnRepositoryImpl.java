package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibraryItemOwnRepositoryImpl implements LibraryItemOwnRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int countAllItems() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM library_item", Integer.class);
    }

    @Override
    public List<LibraryItem> findItemsPublishedAfter(int year) {
        return jdbcTemplate.query(
                "SELECT * FROM library_item WHERE published_year > ?",
                (rs, rowNum) -> {
                    if (rs.getString("isbn") != null) {
                        return new Book(
                                rs.getString("title"),
                                rs.getInt("published_year"),
                                rs.getBoolean("available"),
                                rs.getString("isbn"),
                                rs.getString("author")
                        );
                    } else {
                        return new Magazine(
                                rs.getString("title"),
                                rs.getInt("published_year"),
                                rs.getBoolean("available"),
                                rs.getInt("issue_number"),
                                rs.getString("month")
                        );
                    }
                },
                year
        );
    }

    @Override
    public List<String> findAllBookAuthors() {
        return jdbcTemplate.queryForList(
                "SELECT DISTINCT author FROM library_item WHERE isbn IS NOT NULL",
                String.class
        );
    }
}
