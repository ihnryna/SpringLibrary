package ihnryna.springlibrary.service;

import ihnryna.springlibrary.model.AuthorStats;
import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;
import static org.jooq.Record.*;


@Component
@Transactional
public class AdvancedQueryService {

    @PersistenceContext
    private EntityManager em;

    private final DSLContext dsl;

    public AdvancedQueryService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public int deleteByPublishedYearLessThanJPQL(Integer year) {
        return em.createQuery("DELETE FROM LibraryItem li WHERE li.publishedYear < :year")
                .setParameter("year", year)
                .executeUpdate();
    }

    public int deleteByPublishedYearLessThanNamedQuery(Integer year) {
        return em.createNamedQuery("LibraryItem.deleteByPublishedYearLessThan")
                .setParameter("year", year)
                .executeUpdate();
    }

    public int deleteByPublishedYearLessThanCriteriaAPI(Integer year) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<LibraryItem> delete = cb.createCriteriaDelete(LibraryItem.class);
        Root<LibraryItem> root = delete.from(LibraryItem.class);
        delete.where(cb.lessThan(root.get("publishedYear"), year));

        return em.createQuery(delete).executeUpdate();
    }

    public int deleteByPublishedYearLessThanNativeQuery(Integer year) {
        return em.createNativeQuery("DELETE FROM library_item WHERE published_year < ?")
                .setParameter(1, year)
                .executeUpdate();
    }

    public int deleteByPublishedYearLessThanJOOQ(Integer year) {
        return dsl.deleteFrom(table("library_item"))
                .where(field("published_year", Integer.class).lt(year))
                .execute();
    }


    public int updateAvailableFalseByAuthorJPQL(String author) {
        return em.createQuery("UPDATE Book b " +
                        "SET b.available = false " +
                        "WHERE b.author = :author")
                .setParameter("author", author)
                .executeUpdate();
    }

    public int updateAvailableFalseByAuthorNamedQuery(String author) {
        return em.createNamedQuery("Book.updateAvailableFalseByAuthor")
                .setParameter("author", author)
                .executeUpdate();
    }

    public int updateAvailableFalseByAuthorCriteriaAPI(String author) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Book> update = cb.createCriteriaUpdate(Book.class);
        Root<Book> root = update.from(Book.class);

        update.set(root.get("available"), false)
                .where(cb.equal(root.get("author"), author));

        return em.createQuery(update).executeUpdate();
    }

    public int updateAvailableFalseByAuthorNativeQuery(String author) {
        return em.createNativeQuery("UPDATE library_item SET available = false WHERE author = ?")
                .setParameter(1, author)
                .executeUpdate();
    }

    public int updateAvailableFalseByAuthorJOOQ(String author) {
        return dsl.update(table("library_item"))
                .set(field("available", Boolean.class), false)
                .where(field("author", String.class).equal(author))
                .execute();
    }

    public List<Integer> findYearsWithMoreThanNLibraryItemsJPQL(long n) {
        return em.createQuery("""
                            SELECT DISTINCT c.publishedYear FROM LibraryItem c
                            WHERE (SELECT COUNT(li) FROM LibraryItem li WHERE li.publishedYear = c.publishedYear) > :n
                        """, Integer.class)
                .setParameter("n", n)
                .getResultList();
    }

    public List<Integer> findYearsWithMoreThanNLibraryItemsNamedQuery(long n) {
        return em.createNamedQuery("LibraryItem.findYearsWithMoreThanNLibraryItems")
                .setParameter("n", n)
                .getResultList();
    }

    public List<Integer> findYearsWithMoreThanNLibraryItemsCriteriaAPI(long n) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
        Root<LibraryItem> root = cq.from(LibraryItem.class);

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<LibraryItem> subRoot = subquery.from(LibraryItem.class);
        subquery.select(cb.count(subRoot))
                .where(cb.equal(subRoot.get("publishedYear"), root.get("publishedYear")));

        cq.select(root.get("publishedYear"))
                .where(cb.gt(subquery, n))
                .distinct(true);

        return em.createQuery(cq).getResultList();
    }

    public List<Integer> findYearsWithMoreThanNLibraryItemsNativeQuery(long n) {
        return em.createNativeQuery("""
                        SELECT DISTINCT published_year
                        FROM library_item
                        GROUP BY published_year
                        HAVING COUNT(*) > :n
                        """)
                .setParameter("n", n)
                .getResultList();
    }

    public List<Integer> findYearsWithMoreThanNLibraryItemsJOOQ(long n) {
        return dsl.select(field("published_year", Integer.class))
                .from(table("library_item"))
                .groupBy(field("published_year"))
                .having(count().gt((int) n))
                .fetch(field("published_year", Integer.class));
    }


    public List<Object[]> maxYearByAuthorJPQL() {
        return em.createQuery(
                """
                        SELECT DISTINCT b.author, MAX(b.publishedYear) 
                                                  FROM Book b
                                                  GROUP BY b.author
                                                  ORDER BY b.author
                        """
        ).getResultList();
    }

    public List<Object[]> maxYearByAuthorNamedQuery() {
        return em.createNamedQuery("Book.maxYearByAuthor")
                .getResultList();
    }

    public List<Object[]> maxYearByAuthorCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Book> book = cq.from(Book.class);

        cq.multiselect(
                        book.get("author"),
                        cb.max(book.get("publishedYear"))
                )
                .groupBy(book.get("author"))
                .orderBy(cb.asc(book.get("author")));

        return em.createQuery(cq).getResultList();
    }

    public List<Object[]> maxYearByAuthorNativeQuery() {
        return em.createNativeQuery("""
                        SELECT DISTINCT author, MAX(published_year) 
                        FROM library_item
                        WHERE author IS NOT NULL
                        GROUP BY author 
                        ORDER BY author
                        """)
                .getResultList();
    }

    public List<Object[]> maxYearByAuthorJOOQ() {
        return Arrays.asList(dsl.select(field("author"), max(field("published_year", Integer.class)))
                .from(table("library_item"))
                .where(field("author").isNotNull())
                .groupBy(field("author"))
                .orderBy(field("author"))
                .fetch()
                .intoArrays());
    }


    public List<Object[]> findAuthorAvailableBooksNumberJPQL() {
        return em.createQuery("""
                        SELECT a.authorName, COUNT(DISTINCT (CASE WHEN b.available = true THEN b.id END)) AS availableBooks
                        FROM AuthorStats a
                        LEFT JOIN Book b ON b.author = a.authorName
                        GROUP BY a.authorName
                        ORDER BY a.authorName
                        """, Object[].class)
                .getResultList();
    }

    public List<Object[]> findAuthorAvailableBooksNumberNamedQuery() {
        return em.createNamedQuery("AuthorStats.findAuthorAvailableBooks", Object[].class)
                .getResultList();
    }

    public List<Object[]> findAuthorAvailableBooksNumberCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

        Root<AuthorStats> author = cq.from(AuthorStats.class);
        Root<Book> book = cq.from(Book.class);
        Predicate joinCondition = cb.equal(book.get("author"), author.get("authorName"));
        Predicate availableCondition = cb.isTrue(book.get("available"));

        cq.multiselect(
                        author.get("authorName"),
                        cb.count(cb.selectCase().when(availableCondition, book.get("id")))
                )
                .where(joinCondition) // це імітує LEFT JOIN
                .groupBy(author.get("authorName"))
                .orderBy(cb.asc(author.get("authorName")));

        return em.createQuery(cq).getResultList();
    }

    public List<Object[]> findAuthorAvailableBooksNumberNativeQuery() {
        return em.createNativeQuery("""
                        SELECT a.author_name,
                               COUNT(DISTINCT CASE WHEN b.available = TRUE THEN b.id END) AS available_books
                        FROM author_stats a
                        LEFT JOIN library_item b ON b.author = a.author_name
                        GROUP BY a.author_name
                        ORDER BY a.author_name
                        """)
                .getResultList();
    }

    public List<Object[]> findAuthorAvailableBooksNumberJOOQ() {
        return Arrays.asList(dsl.select(
                        field("a.author_name"),
                        countDistinct(when(field("b.available").eq(true), field("b.id"))).cast(Long.class)
                )
                .from(table("author_stats").as("a"))
                .leftJoin(table("library_item").as("b"))
                .on(field("b.author").eq(field("a.author_name")))
                .groupBy(field("a.author_name"))
                .orderBy(field("a.author_name"))
                .fetch()
                .intoArrays());
    }


    public List<LibraryItem> findLibraryItemsWithTitleAndYearFiltersJPQL(String partOfTitle, Integer minYear) {
        StringBuilder jpql = new StringBuilder("SELECT li FROM LibraryItem li WHERE li.title=li.title ");

        if (partOfTitle != null && !partOfTitle.isEmpty())
            jpql.append("AND li.title LIKE :title ");
        if (minYear != null)
            jpql.append("AND li.publishedYear >= :minYear ");

        TypedQuery<LibraryItem> query = em.createQuery(jpql.toString(), LibraryItem.class);

        if (partOfTitle != null && !partOfTitle.isEmpty())
            query.setParameter("title", "%" + partOfTitle + "%");
        if (minYear != null)
            query.setParameter("minYear", minYear);

        return query.getResultList();
    }

    public List<LibraryItem> findLibraryItemsWithTitleAndYearFiltersCriteriaAPI(String partOfTitle, Integer minYear) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LibraryItem> cq = cb.createQuery(LibraryItem.class);
        Root<LibraryItem> li = cq.from(LibraryItem.class);

        List<Predicate> predicates = new ArrayList<>();

        if (partOfTitle != null && !partOfTitle.isEmpty()) {
            predicates.add(cb.like(li.get("title"), "%" + partOfTitle + "%"));
        }
        if (minYear != null) {
            predicates.add(cb.ge(li.get("publishedYear"), minYear));
        }

        cq.select(li).where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    public List<LibraryItem> findLibraryItemsWithTitleAndYearFiltersNativeQuery(String partOfTitle, Integer minYear) {
        StringBuilder sql = new StringBuilder("SELECT * FROM library_item li WHERE 1=1 ");

        if (partOfTitle != null && !partOfTitle.isEmpty()) {
            sql.append("AND li.title LIKE :title ");
        }
        if (minYear != null) {
            sql.append("AND li.published_year >= :minYear ");
        }

        Query query = em.createNativeQuery(sql.toString(), LibraryItem.class);

        if (partOfTitle != null && !partOfTitle.isEmpty()) {
            query.setParameter("title", "%" + partOfTitle + "%");
        }
        if (minYear != null) {
            query.setParameter("minYear", minYear);
        }

        return query.getResultList();
    }

    public List<LibraryItem> findLibraryItemsWithTitleAndYearFiltersJOOQ(String partOfTitle, Integer minYear) {
        List<Condition> conditions = new ArrayList<>();

        if (partOfTitle != null && !partOfTitle.isEmpty()) {
            conditions.add(field("title").like("%" + partOfTitle + "%"));
        }
        if (minYear != null) {
            conditions.add(field("published_year", Integer.class).ge(minYear));
        }

        return dsl.select()
                .from(table("library_item"))
                .where(conditions)
                .fetch()
                .map(r -> {
                    String dtype = r.get("dtype", String.class);
                    if ("Book".equals(dtype)) {
                        Book b = new Book();
                        b.setId(r.get("id", Long.class));
                        b.setTitle(r.get("title", String.class));
                        b.setPublishedYear(r.get("published_year", Integer.class));
                        b.setAvailable(r.get("available", Boolean.class));
                        b.setIsbn(r.get("isbn", String.class));
                        b.setAuthor(r.get("author", String.class));
                        return b;
                    } else if ("Magazine".equals(dtype)) {
                        Magazine m = new Magazine();
                        m.setId(r.get("id", Long.class));
                        m.setTitle(r.get("title", String.class));
                        m.setPublishedYear(r.get("published_year", Integer.class));
                        m.setAvailable(r.get("available", Boolean.class));
                        m.setIssueNumber(r.get("issue_number", Integer.class));
                        m.setMonth(r.get("month", String.class));
                        return m;
                    } else {
                        return null;
                    }
                });

    }

}
