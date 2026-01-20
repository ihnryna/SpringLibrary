package ihnryna.springlibrary.model;

import jakarta.persistence.Entity;

@Entity
public class Magazine extends LibraryItem {

    private Integer issueNumber;
    private String month;

    public Magazine() {
    }

    public Magazine(String title, Integer publishedYear, Boolean available, Integer issueNumber, String month) {
        super(title, publishedYear, available);
        this.issueNumber = issueNumber;
        this.month = month;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
