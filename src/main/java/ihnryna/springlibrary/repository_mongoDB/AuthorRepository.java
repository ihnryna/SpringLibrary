package ihnryna.springlibrary.repository_mongoDB;

import ihnryna.springlibrary.model_mongoDB.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByAuthorName(String authorName);
    List<Author> findByAuthorNameContaining(String authorName);
    List<Author> findByCountry(String country);
}