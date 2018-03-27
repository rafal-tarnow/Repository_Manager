package pl.zmt.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.manager.entity.Set;

import java.util.Collection;

public interface SetRepository extends JpaRepository<Set, Integer> {

    Collection<Set> findByName(String name);
}
