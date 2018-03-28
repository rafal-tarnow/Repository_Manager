package pl.zmt.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.manager.entity.Set;

public interface SetRepository extends JpaRepository<Set, Integer> {

    Set findByName(String name);
}
