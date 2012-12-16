package be.codinsanity.quickstart.repository;

import be.codinsanity.quickstart.domain.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DummyRepository extends JpaRepository<Dummy, Long> {
}
