package pl.zmt.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.manager.entities.Repo;

public interface RepoRepository extends JpaRepository<Repo,Long>{

    Repo findByIndexRepo(String index);

    Repo findFirstByOrderByIdDesc();
}
