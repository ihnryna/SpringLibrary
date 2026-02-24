package ihnryna.springlibrary.model_mongoDB;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    private String  id;
    private String authorName;
    private String country;
    private boolean active;

    public Author(String authorName, String country, boolean active) {
        this.authorName = authorName;
        this.country = country;
        this.active = active;
    }
}
