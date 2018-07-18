package pl.zmt.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.manager.entities.Set;

import java.util.Optional;

public interface SetRepository extends JpaRepository<Set, Integer> {

    Set findByName(String name);

    Optional<Set> findById(Integer id);

    java.util.Set<Set> findByIdAndSets(Integer set, java.util.Set<Set> sets);

}
