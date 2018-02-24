package pl.zmt.menager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zmt.menager.entity.Repo;

public interface RepoRepository extends JpaRepository<Repo,Integer>{

    Repo findByIndexRepo(String index);
}
