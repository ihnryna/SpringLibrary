package ihnryna.springlibrary.dto;

public class MagazineDto extends LibraryItemDto {
    private Integer issueNumber;
    private String month;

    public MagazineDto() {
    }

    public MagazineDto(Integer issueNumber, String month) {
        this.issueNumber = issueNumber;
        this.month = month;
    }

    public MagazineDto(Long id, String title, Integer publishedYear, Boolean available, Integer issueNumber, String month) {
        super(id, title, publishedYear, available);
        this.issueNumber = issueNumber;
        this.month = month;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
