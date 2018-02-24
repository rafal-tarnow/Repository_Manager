package pl.zmt.menager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zmt.menager.entity.Repo;
import pl.zmt.menager.repository.RepoRepository;

import java.util.Collection;

@Service
public class RepoService {

    @Autowired
    private RepoRepository repoRepository;

    public Collection<Repo> findAll() {
        return repoRepository.findAll();
    }

    public void save(String index, String name, String description){
        Repo repo = new Repo();
        repo.setId(repoRepository.count()+1);
        repo.setIndexRepo(index);
        repo.setName(name);
        repo.setDescription(description);
        repo.setLocation("/srv/repos/git/" + repo.getIndexRepo());
        repoRepository.save(repo);
    }
}
