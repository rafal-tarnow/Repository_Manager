package pl.zmt.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zmt.manager.entities.Composition;
import pl.zmt.manager.entities.Set;
import pl.zmt.manager.repositories.SetRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

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

    public void deleteComponent(Integer id_set, Integer id_component) {
        Set c = setRepository.findById(id_component).get();
        Set set = setRepository.findById(id_set).get();
        set.getSets().remove(c);
        setRepository.save(set);
    }

    public void add(Integer id_set, Integer id_component) {
        Set c = setRepository.findById(id_component).get();
        Set set = setRepository.findById(id_set).get();
        set.getSets().add(c);
        setRepository.save(set);
    }

    public Object findAllUnusedSet(String name, java.util.Set<Set> usedSet) {
        Set parrent = setRepository.findByName(name);
        java.util.Set<Set> allSet = new HashSet<>(setRepository.findAll());
        allSet.remove(parrent);
        for(Set x : usedSet)
            allSet.remove(x);
        return allSet;
    }
}
