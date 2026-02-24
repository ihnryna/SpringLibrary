package ihnryna.springlibrary.model_mongoDB;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("readers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {
    @Id
    private String id;
    private String surname;
    private Integer balance;
}
