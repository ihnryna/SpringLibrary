package ihnryna.springlibrary.model;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQuery(name = "LibraryItem.deleteByPublishedYearLessThan",
        query = "DELETE FROM LibraryItem li WHERE li.publishedYear < :year")
@NamedQuery(name = "LibraryItem.findYearsWithMoreThanNLibraryItems",
        query = "SELECT DISTINCT c.publishedYear FROM LibraryItem c\n" +
                "WHERE (SELECT COUNT(li) FROM LibraryItem li WHERE li.publishedYear = c.publishedYear) > :n")
public abstract class LibraryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;
    protected Integer publishedYear;
    protected Boolean available;

    public LibraryItem() {}

    protected LibraryItem(String title, Integer publishedYear, Boolean available) {
        this.title = title;
        this.publishedYear = publishedYear;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }
}
