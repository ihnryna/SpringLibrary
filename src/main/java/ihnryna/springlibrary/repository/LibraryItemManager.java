package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.LibraryItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class LibraryItemManager {

    @PersistenceContext
    private EntityManager entityManager;

    // entity becomes managed, changes auto sync in db
    public void persist(LibraryItem item) {
        entityManager.persist(item);
    }

    public LibraryItem find(Long id) {
        return entityManager.find(LibraryItem.class, id);
    }

    // entity loses connection with db
    public void detach(LibraryItem item) {
        entityManager.detach(item);
    }

    // data deletes from db after commit
    public void remove(LibraryItem item) {
        entityManager.remove(item);
    }

    // rewrite data from db to this entity
    public void refresh(LibraryItem item) {
        entityManager.refresh(item);
    }

    // returns new managed entity from unmanaged entity
    public LibraryItem merge(LibraryItem item) {
        return entityManager.merge(item);
    }
}
