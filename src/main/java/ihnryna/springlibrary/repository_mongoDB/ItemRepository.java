package ihnryna.springlibrary.repository_mongoDB;

import ihnryna.springlibrary.model_mongoDB.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{ $and: [ { 'available': true }, { 'publishedYear': { $gte: ?0 } } ] }")
    List<Item> findAvailableItemsPublishedAfter(Integer year);

    @Query("{ 'publishedYear': { $in: ?0 } }")
    List<Item> findItemsByPublishedYears(List<Integer> years);

    @Query("{ 'title' : { $regex: ?0, $options: 'i' } }")
    List<Item> findByTitleRegex(String regex);
}