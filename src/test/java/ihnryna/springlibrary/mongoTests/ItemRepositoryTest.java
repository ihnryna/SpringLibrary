package ihnryna.springlibrary.mongoTests;

import ihnryna.springlibrary.model_mongoDB.Item;
import ihnryna.springlibrary.repository_mongoDB.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("mongo")
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setup() {
        itemRepository.deleteAll();

        itemRepository.save(new Item("Modern Java", 2021, true));
        itemRepository.save(new Item("Old Algorithms", 1995, true));
        itemRepository.save(new Item("Unavailable Book with guides", 2024, false));
        itemRepository.save(new Item("Ancient History", 1980, false));
        itemRepository.save(new Item("Spring Boot Guide", 2023, true));
    }

    @Test
    void shouldFindAvailableItemsPublishedAfter() {
        List<Item> items = itemRepository.findAvailableItemsPublishedAfter(2020);

        assertThat(items).hasSize(2);
        assertThat(items)
                .extracting(Item::getTitle)
                .containsExactlyInAnyOrder("Modern Java", "Spring Boot Guide");
    }

    @Test
    void shouldFindItemsByPublishedYears() {
        List<Item> items = itemRepository.findItemsByPublishedYears(List.of(1995, 1980, 2000));
        assertThat(items).hasSize(2);
        assertThat(items)
                .extracting(Item::getTitle)
                .containsExactlyInAnyOrder("Old Algorithms", "Ancient History");
    }

    @Test
    void shouldFindUnavailableOrOldItems() {
        List<Item> items = itemRepository.findByTitleRegex(".*guide.*");
        assertThat(items).hasSize(2);
        assertThat(items)
                .extracting(Item::getTitle)
                .containsExactlyInAnyOrder("Unavailable Book with guides", "Spring Boot Guide");
    }
}
