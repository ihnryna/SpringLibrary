package ihnryna.springlibrary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQuery(name = "AuthorStats.findAuthorAvailableBooks",
        query = """
                SELECT a.authorName, COUNT(DISTINCT (CASE WHEN b.available = true THEN b.id END)) AS availableBooks
                FROM AuthorStats a
                LEFT JOIN Book b ON b.author = a.authorName
                GROUP BY a.authorName
                ORDER BY a.authorName
                """)
public class AuthorStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    private String country;

    private boolean active;

    @Column(name = "books_count", nullable = false)
    private int booksCount;

    @Column(name = "earliest_publish_year", nullable = false)
    private int earliestPublishYear;
}
