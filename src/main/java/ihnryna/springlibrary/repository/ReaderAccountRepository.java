package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.ReaderAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderAccountRepository extends JpaRepository<ReaderAccount, Long> {
    Optional<ReaderAccount> findBySurname(String surname);
}