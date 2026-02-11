package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.LibraryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LibraryItemRepository extends JpaRepository<LibraryItem, Long>, LibraryItemOwnRepository {
    Optional<Object> findLibraryItemByTitle(String title);

    @Modifying
    @Transactional
    @Query("""
        UPDATE LibraryItem li
        SET li.available = false
        WHERE li.title = :title
    """)
    void markUnavailableByTitle(String title);
}
