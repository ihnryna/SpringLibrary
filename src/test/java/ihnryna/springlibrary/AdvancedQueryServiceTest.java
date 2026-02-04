package ihnryna.springlibrary;

import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.service.AdvancedQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


@SpringBootTest
@Testcontainers
@Sql("/data3.sql")
public class AdvancedQueryServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private AdvancedQueryService service;

    @Autowired
    private EntityManager em;

    @Test
    void deleteByPublishedYearLessThanJPQL_deletesOnlyOldBooks() {
        int deleted = service.deleteByPublishedYearLessThanJPQL(2010);
        assertThat(deleted).isEqualTo(4);

        List<LibraryItem> remainingBooks = em.createQuery("SELECT li FROM LibraryItem li", LibraryItem.class).getResultList();
        assertThat(remainingBooks).allMatch(li -> li.getPublishedYear() >= 2010);
    }

    @Test
    void deleteByPublishedYearLessThanNamedQuery_deletesOnlyOldBooks() {
        int deleted = service.deleteByPublishedYearLessThanNamedQuery(2010);
        assertThat(deleted).isEqualTo(4);

        List<LibraryItem> remaining = em.createQuery("SELECT li FROM LibraryItem li", LibraryItem.class).getResultList();
        assertThat(remaining).allMatch(li -> li.getPublishedYear() >= 2010);
    }

    @Test
    void deleteByPublishedYearLessThanCriteriaAPI_deletesOnlyOldBooks() {
        int deleted = service.deleteByPublishedYearLessThanCriteriaAPI(2010);
        assertThat(deleted).isEqualTo(4);

        List<LibraryItem> remaining = em.createQuery("SELECT li FROM LibraryItem li", LibraryItem.class).getResultList();
        assertThat(remaining).allMatch(li -> li.getPublishedYear() >= 2010);
    }

    @Test
    void deleteByPublishedYearLessThanNativeQuery_deletesOnlyOldBooks() {
        int deleted = service.deleteByPublishedYearLessThanNativeQuery(2010);
        assertThat(deleted).isEqualTo(4);

        List<LibraryItem> remaining = em.createQuery("SELECT li FROM LibraryItem li", LibraryItem.class).getResultList();
        assertThat(remaining).allMatch(li -> li.getPublishedYear() >= 2010);
    }

    @Test
    void deleteByPublishedYearLessThanJOOQ_deletesOnlyOldBooks() {
        int deleted = service.deleteByPublishedYearLessThanJOOQ(2010);
        assertThat(deleted).isEqualTo(4);

        List<LibraryItem> remaining = em.createQuery("SELECT li FROM LibraryItem li", LibraryItem.class).getResultList();
        assertThat(remaining).allMatch(li -> li.getPublishedYear() >= 2010);
    }


    @Test
    void updateAvailableFalseByAuthorJPQL_works() {
        int updated = service.updateAvailableFalseByAuthorJPQL("Robert C. Martin");
        assertThat(updated).isEqualTo(2);

        List<LibraryItem> items = em.createQuery("SELECT b FROM Book b WHERE b.author = 'Robert C. Martin'",
                LibraryItem.class).getResultList();

        for (LibraryItem li : items) {
            assertThat(li.getAvailable()).isFalse();
        }
    }

    @Test
    void updateAvailableFalseByAuthorNamedQuery_works() {
        int updated = service.updateAvailableFalseByAuthorNamedQuery("Robert C. Martin");
        assertThat(updated).isEqualTo(2);

        List<LibraryItem> items = em.createQuery("SELECT b FROM Book b WHERE b.author = 'Robert C. Martin'",
                LibraryItem.class).getResultList();
        for (LibraryItem li : items) {
            assertThat(li.getAvailable()).isFalse();
        }
    }

    @Test
    void updateAvailableFalseByAuthorCriteriaAPI_works() {
        int updated = service.updateAvailableFalseByAuthorCriteriaAPI("Robert C. Martin");
        assertThat(updated).isEqualTo(2);

        List<LibraryItem> items = em.createQuery("SELECT b FROM Book b WHERE b.author = 'Robert C. Martin'",
                LibraryItem.class).getResultList();
        for (LibraryItem li : items) {
            assertThat(li.getAvailable()).isFalse();
        }
    }

    @Test
    void updateAvailableFalseByAuthorNativeQuery_works() {
        int updated = service.updateAvailableFalseByAuthorNativeQuery("Robert C. Martin");
        assertThat(updated).isEqualTo(2);

        List<LibraryItem> items = em.createQuery("SELECT b FROM Book b WHERE b.author = 'Robert C. Martin'",
                LibraryItem.class).getResultList();
        for (LibraryItem li : items) {
            assertThat(li.getAvailable()).isFalse();
        }
    }

    @Test
    void updateAvailableFalseByAuthorJOOQ_works() {
        int updated = service.updateAvailableFalseByAuthorJOOQ("Robert C. Martin");
        assertThat(updated).isEqualTo(2);

        List<LibraryItem> items = em.createQuery("SELECT b FROM Book b WHERE b.author = 'Robert C. Martin'",
                LibraryItem.class).getResultList();
        for (LibraryItem li : items) {
            assertThat(li.getAvailable()).isFalse();
        }
    }



    @Test
    public void findYearsWithMoreThanNLibraryItemsJPQL_works() {
        List<Integer> result = service.findYearsWithMoreThanNLibraryItemsJPQL(1);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(2018);
        assertThat(result).contains(2021);
    }

    @Test
    public void findYearsWithMoreThanNLibraryItemsNamedQuery_works() {
        List<Integer> result = service.findYearsWithMoreThanNLibraryItemsNamedQuery(1);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(2018);
        assertThat(result).contains(2021);
    }

    @Test
    public void findYearsWithMoreThanNLibraryItemsCriteriaAPI_works() {
        List<Integer> result = service.findYearsWithMoreThanNLibraryItemsCriteriaAPI(1);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(2018);
        assertThat(result).contains(2021);
    }

    @Test
    public void findYearsWithMoreThanNLibraryItemsNativeQuery_works() {
        List<Integer> result = service.findYearsWithMoreThanNLibraryItemsNativeQuery(1);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(2018);
        assertThat(result).contains(2021);
    }

    @Test
    public void findYearsWithMoreThanNLibraryItemsJOOQ_works() {
        List<Integer> result = service.findYearsWithMoreThanNLibraryItemsJOOQ(1);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(2018);
        assertThat(result).contains(2021);
    }



    private void maxYearByAuthorAssertions(List<Object[]> result) {
        Assertions.assertEquals(7, result.size());
        Assertions.assertEquals("Brian Goetz", result.get(0)[0]);
        Assertions.assertEquals(2006, result.get(0)[1]);
        Assertions.assertEquals("Craig Walls", result.get(1)[0]);
        Assertions.assertEquals(2021, result.get(1)[1]);
        Assertions.assertEquals("Erich Gamma", result.get(2)[0]);
        Assertions.assertEquals(1994, result.get(2)[1]);
        Assertions.assertEquals("Joshua Bloch", result.get(3)[0]);
        Assertions.assertEquals(2018, result.get(3)[1]);
        Assertions.assertEquals("Martin Fowler", result.get(4)[0]);
        Assertions.assertEquals(2018, result.get(4)[1]);
        Assertions.assertEquals("Robert C. Martin", result.get(5)[0]);
        Assertions.assertEquals(2017, result.get(5)[1]);
        Assertions.assertEquals("Thorben Janssen", result.get(6)[0]);
        Assertions.assertEquals(2018, result.get(6)[1]);
    }

    @Test
    public void maxYearByAuthorJPQL_works() {
        List<Object[]> result = service.maxYearByAuthorJPQL();
        maxYearByAuthorAssertions(result);
    }

    @Test
    public void maxYearByAuthorNamedQuery_works() {
        List<Object[]> result = service.maxYearByAuthorNamedQuery();
        maxYearByAuthorAssertions(result);
    }

    @Test
    public void maxYearByAuthorCriteriaAPI_works() {
        List<Object[]> result = service.maxYearByAuthorCriteriaAPI();
        maxYearByAuthorAssertions(result);
    }

    @Test
    public void maxYearByAuthorNativeQuery_works() {
        List<Object[]> result = service.maxYearByAuthorNativeQuery();
        maxYearByAuthorAssertions(result);
    }

    @Test
    public void maxYearByAuthorJOOQ_works() {
        List<Object[]> result = service.maxYearByAuthorJOOQ();
        maxYearByAuthorAssertions(result);
    }



    private void findAuthorAvailableBooksNumberAssertions(List<Object[]> result) {
        Assertions.assertEquals(7, result.size());
        Assertions.assertEquals("Brian Goetz", result.get(0)[0]);
        Assertions.assertEquals(0L, result.get(0)[1]);
        Assertions.assertEquals("Craig Walls", result.get(1)[0]);
        Assertions.assertEquals(1L, result.get(1)[1]);
        Assertions.assertEquals("Erich Gamma", result.get(2)[0]);
        Assertions.assertEquals(1L, result.get(2)[1]);
        Assertions.assertEquals("Joshua Bloch", result.get(3)[0]);
        Assertions.assertEquals(1L, result.get(3)[1]);
        Assertions.assertEquals("Martin Fowler", result.get(4)[0]);
        Assertions.assertEquals(1L, result.get(4)[1]);
        Assertions.assertEquals("Robert C. Martin", result.get(5)[0]);
        Assertions.assertEquals(1L, result.get(5)[1]);
        Assertions.assertEquals("Thorben Janssen", result.get(6)[0]);
        Assertions.assertEquals(1L, result.get(6)[1]);
    }

    @Test
    public void findAuthorAvailableBooksNumberJPQL_works() {
        List<Object[]> result = service.findAuthorAvailableBooksNumberJPQL();
        findAuthorAvailableBooksNumberAssertions(result);
    }

    @Test
    public void findAuthorAvailableBooksNumberNamedQuery_works() {
        List<Object[]> result = service.findAuthorAvailableBooksNumberNamedQuery();
        findAuthorAvailableBooksNumberAssertions(result);
    }

    @Test
    public void findAuthorAvailableBooksNumberCriteriaAPI_works() {
        List<Object[]> result = service.findAuthorAvailableBooksNumberCriteriaAPI();
        findAuthorAvailableBooksNumberAssertions(result);
    }

    @Test
    public void findAuthorAvailableBooksNumberNativeQuery_works() {
        List<Object[]> result = service.findAuthorAvailableBooksNumberNativeQuery();
        findAuthorAvailableBooksNumberAssertions(result);
    }

    @Test
    public void findAuthorAvailableBooksNumberJOOQ_works() {
        List<Object[]> result = service.findAuthorAvailableBooksNumberJOOQ();
        findAuthorAvailableBooksNumberAssertions(result);
    }







    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersJPQL_partOfTitle() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersJPQL("Clean", null);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getTitle().contains("Clean")));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersJPQL_minYear() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersJPQL(null, 2020);

        Assertions.assertEquals(6, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getPublishedYear() >= 2020));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersJPQL_bothParameters() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersJPQL("Java", 2010);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Effective Java", result.getFirst().getTitle());
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersCriteriaAPI_partOfTitle() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersCriteriaAPI("Clean", null);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getTitle().contains("Clean")));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersCriteriaAPI_minYear() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersCriteriaAPI(null, 2020);

        Assertions.assertEquals(6, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getPublishedYear() >= 2020));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersCriteriaAPI_bothParameters() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersCriteriaAPI("Java", 2010);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Effective Java", result.getFirst().getTitle());
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersNativeQuery_partOfTitle() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersNativeQuery("Clean", null);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getTitle().contains("Clean")));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersNativeQuery_minYear() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersNativeQuery(null, 2020);

        Assertions.assertEquals(6, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getPublishedYear() >= 2020));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersNativeQuery_bothParameters() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersNativeQuery("Java", 2010);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Effective Java", result.getFirst().getTitle());
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersJOOQ_partOfTitle() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersJOOQ("Clean", null);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getTitle().contains("Clean")));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersJOOQ_minYear() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersJOOQ(null, 2020);

        Assertions.assertEquals(6, result.size());
        Assertions.assertTrue(result.stream().allMatch(li -> li.getPublishedYear() >= 2020));
    }

    @Test
    public void testFindLibraryItemsWithTitleAndYearFiltersJOOQ_bothParameters() {
        List<LibraryItem> result = service.findLibraryItemsWithTitleAndYearFiltersJOOQ("Java", 2010);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Effective Java", result.getFirst().getTitle());
    }


}
