package ihnryna.springlibrary.repository_mongoDB;

import ihnryna.springlibrary.model_mongoDB.Reader;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;


@Repository
public class ReaderOwnRepository {

    private final MongoTemplate mongoTemplate;

    public ReaderOwnRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Reader> findBySurname(String surname) {
        return mongoTemplate.query(Reader.class)
                .inCollection("readers")
                .matching(query(where("surname").is(surname)))
                .first();
    }

    public List<Reader> findByBalanceGreaterThan(int amount) {
        return mongoTemplate.query(Reader.class)
                .inCollection("readers")
                .matching(query(where("balance").gt(amount)))
                .all();
    }

    public List<Reader> findBySurnameStartingWith(String prefix) {
        return mongoTemplate.query(Reader.class)
                .inCollection("readers")
                .matching(query(where("surname").regex("^" + prefix, "i")))
                .all();
    }
}