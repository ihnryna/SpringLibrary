package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.LibraryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryItemRepository extends JpaRepository<LibraryItem, Long>, LibraryItemOwnRepository {
}
