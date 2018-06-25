package pl.zmt.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.manager.entities.Set;

public interface SetRepository extends JpaRepository<Set, Integer> {

    Set findByName(String name);
}
