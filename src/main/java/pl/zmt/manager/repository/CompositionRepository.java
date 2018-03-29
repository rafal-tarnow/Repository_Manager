package pl.zmt.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.manager.entity.Composition;

import java.util.Collection;

public interface CompositionRepository extends JpaRepository<Composition,Integer> {
    Collection<Composition> findAllBySet(Integer id_set);

    Composition findBySetAndRepository(Integer set, Long repository);
}
