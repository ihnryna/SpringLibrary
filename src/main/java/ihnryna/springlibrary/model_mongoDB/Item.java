package ihnryna.springlibrary.model_mongoDB;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    protected String id;
    protected String title;
    protected Integer publishedYear;
    protected Boolean available;

    public Item(String title, Integer publishedYear, Boolean available) {
        this.title = title;
        this.publishedYear = publishedYear;
        this.available = available;
    }
}
