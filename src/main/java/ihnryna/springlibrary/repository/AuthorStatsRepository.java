package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.AuthorStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorStatsRepository extends JpaRepository<AuthorStats, Long> {
}