package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LibraryItemDAOImpl implements LibraryItemDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<LibraryItem> itemRowMapper = (rs, rowNum) -> {
        LibraryItem item = new LibraryItem();
        item.setId(rs.getLong("id"));
        item.setTitle(rs.getString("title"));
        item.setPublishedYear(rs.getInt("published_year"));
        item.setAvailable(rs.getBoolean("available"));
        return item;
    };

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setPublishedYear(rs.getInt("published_year"));
        book.setAvailable(rs.getBoolean("available"));
        book.setIsbn(rs.getString("isbn"));
        book.setAuthor(rs.getString("author"));
        return book;
    };

    private final RowMapper<Magazine> magazineRowMapper = (rs, rowNum) -> {
        Magazine magazine = new Magazine();
        magazine.setId(rs.getLong("id"));
        magazine.setTitle(rs.getString("title"));
        magazine.setPublishedYear(rs.getInt("published_year"));
        magazine.setAvailable(rs.getBoolean("available"));
        magazine.setIssueNumber(rs.getInt("issue_number"));
        magazine.setMonth(rs.getString("month"));
        return magazine;
    };


    @Override
    public void createLibraryItem(LibraryItem item) throws DataAccessException {
        String sqlItem = "INSERT INTO library_item (title, published_year, available) VALUES (?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(
                sqlItem,
                new Object[]{item.getTitle(), item.getPublishedYear(), item.getAvailable()},
                Long.class
        );
        item.setId(id);

        if (item instanceof Book book) {
            String sqlBook = "INSERT INTO book (id, isbn, author) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlBook, id, book.getIsbn(), book.getAuthor());
        }

        if (item instanceof Magazine mag) {
            String sqlMagazine = "INSERT INTO magazine (id, issue_number, month) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlMagazine, id, mag.getIssueNumber(), mag.getMonth());
        }
    }

    @Override
    public LibraryItem getLibraryItemById(Long id) throws DataAccessException {
        String sqlBook = """
            SELECT li.*, b.isbn, b.author
            FROM library_item li
            JOIN book b ON li.id = b.id
            WHERE li.id = ?
        """;
        List<Book> books = jdbcTemplate.query(sqlBook, bookRowMapper, id);
        if (!books.isEmpty()) return books.get(0);

        String sqlMagazine = """
            SELECT li.*, m.issue_number, m.month
            FROM library_item li
            JOIN magazine m ON li.id = m.id
            WHERE li.id = ?
        """;
        List<Magazine> magazines = jdbcTemplate.query(sqlMagazine, magazineRowMapper, id);
        if (!magazines.isEmpty()) return magazines.get(0);

        String sqlItem = "SELECT * FROM library_item WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlItem, itemRowMapper, id);
    }

    @Override
    public List<LibraryItem> listLibraryItems() throws DataAccessException {
        String sql = "SELECT * FROM library_item";
        return jdbcTemplate.query(sql, itemRowMapper);
    }

    @Override
    public void updateLibraryItem(LibraryItem item) throws DataAccessException {
        String sql = "UPDATE library_item SET title = ?, published_year = ?, available = ? WHERE id = ?";
        jdbcTemplate.update(sql, item.getTitle(), item.getPublishedYear(), item.getAvailable(), item.getId());

        if (item instanceof Book book) {
            String sqlBook = "UPDATE book SET isbn = ?, author = ? WHERE id = ?";
            jdbcTemplate.update(sqlBook, book.getIsbn(), book.getAuthor(), book.getId());
        }

        if (item instanceof Magazine mag) {
            String sqlMagazine = "UPDATE magazine SET issue_number = ?, month = ? WHERE id = ?";
            jdbcTemplate.update(sqlMagazine, mag.getIssueNumber(), mag.getMonth(), mag.getId());
        }
    }

    @Override
    public void deleteLibraryItem(Long id) throws DataAccessException {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
        jdbcTemplate.update("DELETE FROM magazine WHERE id = ?", id);
        jdbcTemplate.update("DELETE FROM library_item WHERE id = ?", id);
    }

    @Override
    public List<Book> listBooks() throws DataAccessException {
        String sql = """
            SELECT li.*, b.isbn, b.author
            FROM library_item li
            JOIN book b ON li.id = b.id
        """;
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public List<Magazine> listMagazines() throws DataAccessException {
        String sql = """
            SELECT li.*, m.issue_number, m.month
            FROM library_item li
            JOIN magazine m ON li.id = m.id
        """;
        return jdbcTemplate.query(sql, magazineRowMapper);
    }
}
