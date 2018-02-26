package pl.zmt.menager.services;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zmt.menager.entity.Repo;
import pl.zmt.menager.repository.RepoRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public Repo findByIndex(String index){
        return repoRepository.findByIndexRepo(index);
    }

    public void updateRepo(String index, String description){
        Repo repo = repoRepository.findByIndexRepo(index);
        repo.setDescription(description);
        repoRepository.save(repo);
    }


    private String generateNewIndex(){
        String index = repoRepository.findFirstByOrderByIdDesc().getIndexRepo();

        int numIndex = Integer.parseInt(index.substring(3));
        System.out.println(numIndex + "@2");
        numIndex += 1;

        index = "REP" + numIndex;
        int roznica = 9 - index.length();
        if(roznica > 0) {
            String zera = "";
            for (int i = 0; i < roznica; i++) {
                zera += "0";
            }
            index = "REP" + zera + numIndex;
        }

        System.out.println(index + "@3");
        return index;
    }

    public List<String> returnOptionsList(){
        List<String> list = new ArrayList<>();
        list.add(generateNewIndex());
        return list;
    }

    public void createRepository(String index, String name, String description) throws GitAPIException {
        File file =  new File("/srv/repos/git/" + index);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
                Git git = Git.init().setDirectory(file).setBare( true ).call();
                if(git.getRepository().isBare()){
                    System.out.println("Repository is initialized!");
                    save(index,name,description);
                }else {
                    System.out.println("Failed to initialized repository!");
                }
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }
}
