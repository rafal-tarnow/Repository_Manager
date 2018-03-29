package pl.zmt.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zmt.manager.entity.Composition;
import pl.zmt.manager.entity.Repo;
import pl.zmt.manager.entity.Set;
import pl.zmt.manager.repository.CompositionRepository;

import java.util.*;

@Service
public class CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private SetService setService;
    @Autowired
    private RepoService repoService;

    public List<Repo> findAllBySetName(String name) {
        Set set = setService.findByName(name);
        Collection<Composition> compositions  = compositionRepository.findAllBySet(set.getId());

        List<Repo> repos = new ArrayList<Repo>();

        for (Composition c : compositions
             ) {System.out.println(c.getSet() + " : " + c.getRepository() );
            Optional<Repo> optional = repoService.findById(c.getRepository());
            optional.ifPresent(repos::add);
        }
        return repos;
    }

    public void delete(Integer id_set, Long id_repo) {
        Composition c = compositionRepository.findBySetAndRepository(id_set,id_repo);
        compositionRepository.delete(c);
    }
}
