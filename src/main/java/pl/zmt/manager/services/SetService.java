package pl.zmt.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zmt.manager.entities.Composition;
import pl.zmt.manager.entities.Set;
import pl.zmt.manager.repositories.SetRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SetService {

    @Autowired
    private SetRepository setRepository;

    public Collection<Set> findAll() {
        return setRepository.findAll();
    }

    public Set findByName(String name) { return setRepository.findByName(name); }

    public boolean createRepository(String name, String description) {
        if(setRepository.findByName(name) == null){
            setRepository.save(new Set(name,description));
            return true;
        }else
            return false;
    }

    public void updateRepo(String name, String description) {
        Set set = setRepository.findByName(name);
        set.setDescription(description);
        setRepository.save(set);
    }

    public java.util.Set<Set> findAllComponents(String name) {
        return setRepository.findByName(name).getSets();
    }
}
