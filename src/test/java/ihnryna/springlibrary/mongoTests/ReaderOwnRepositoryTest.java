package ihnryna.springlibrary.mongoTests;

import ihnryna.springlibrary.model_mongoDB.Reader;
import ihnryna.springlibrary.repository_mongoDB.ReaderOwnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReaderOwnRepositoryTest {
    @Autowired
    private ReaderOwnRepository readerRepo;

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Reader.class);

        mongoTemplate.save(new Reader(null, "Ivanov", 100));
        mongoTemplate.save(new Reader(null, "Petrenko", 50));
        mongoTemplate.save(new Reader(null, "Ivanko", 200));
        mongoTemplate.save(new Reader(null, "Sidorov", 0));
        mongoTemplate.save(new Reader(null, "Ivanich", 150));
    }

    @Test
    void testFindBySurname() {
        Optional<Reader> result = readerRepo.findBySurname("Ivanov");
        assertThat(result).isPresent();
        assertThat(result.get().getSurname()).isEqualTo("Ivanov");
    }

    @Test
    void testFindByNonExistingSurname() {
        Optional<Reader> notFound = readerRepo.findBySurname("NonExisting");
        assertThat(notFound).isNotPresent();
    }

    @Test
    void testFindByBalanceGreaterThan() {
        List<Reader> readers = readerRepo.findByBalanceGreaterThan(100);
        assertThat(readers).hasSize(2)
                .extracting(Reader::getSurname)
                .containsExactlyInAnyOrder("Ivanko", "Ivanich");
    }

    @Test
    void testFindBySurnameStartingWith() {
        List<Reader> readers = readerRepo.findBySurnameStartingWith("Iv");
        assertThat(readers).hasSize(3)
                .extracting(Reader::getSurname)
                .containsExactlyInAnyOrder("Ivanov", "Ivanko", "Ivanich");
    }
}
