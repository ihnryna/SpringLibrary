package ihnryna.springlibrary.mongoTests;

import ihnryna.springlibrary.model_mongoDB.Author;
import ihnryna.springlibrary.repository_mongoDB.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("mongo")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setup() {
        authorRepository.deleteAll();

        authorRepository.save(new Author("Taras Shevchenko", "Ukraine", false));
        authorRepository.save(new Author("Ivan Drach", "Ukraine", true));
        authorRepository.save(new Author("Ivan Franko", "Ukraine", true));
        authorRepository.save(new Author("Ernest Hemingway", "USA", false));
    }

    @Test
    void shouldFindAuthorByName() {
        Optional<Author> author = authorRepository.findByAuthorName("Taras Shevchenko");
        assertThat(author).isPresent();
        assertThat(author.get().getCountry()).isEqualTo("Ukraine");
        assertThat(author.get().getAuthorName()).isEqualTo("Taras Shevchenko");
        assertThat(author.get().isActive()).isEqualTo(false);
    }

    @Test
    void shouldFindAuthorsByNameContaining() {
        List<Author> authors = authorRepository.findByAuthorNameContaining("Ivan");

        assertThat(authors).hasSize(2);
        assertThat(authors.getFirst().getAuthorName()).isEqualTo("Ivan Drach");
        assertThat(authors.getLast().getAuthorName()).isEqualTo("Ivan Franko");
    }

    @Test
    void shouldFindAuthorsByCountry() {
        List<Author> authors = authorRepository.findByCountry("Ukraine");
        assertThat(authors).hasSize(3);
        for (Author author : authors) {
            assertThat(author.getCountry()).isEqualTo("Ukraine");
        }
    }
}
