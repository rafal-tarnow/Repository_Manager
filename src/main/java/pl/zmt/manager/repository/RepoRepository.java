package pl.zmt.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.manager.entity.Repo;

public interface RepoRepository extends JpaRepository<Repo,Long>{

    Repo findByIndexRepo(String index);

    Repo findFirstByOrderByIdDesc();
}
