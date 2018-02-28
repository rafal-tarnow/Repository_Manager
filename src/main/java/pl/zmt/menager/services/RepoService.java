package pl.zmt.menager.services;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zmt.menager.Node;
import pl.zmt.menager.entity.Repo;
import pl.zmt.menager.repository.RepoRepository;

import java.io.File;
import java.io.IOException;
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
        numIndex += 1;

        index = "REP" + numIndex;
        int missing = 9 - index.length();
        if(missing > 0) {
            String nulls = "";
            for (int i = 0; i < missing; i++) {
                nulls += "0";
            }
            index = "REP" + nulls + numIndex;
        }

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

    public Node returnTreeNode(String index){
        String path = "/srv/repos/git/" + index;

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Node rootNode = new Node(index);

        try {
            Repository repo = builder.setGitDir(new File(path)).setMustExist(true).build();
            Ref head = repo.getRef("HEAD");
            RevWalk walk = new RevWalk(repo);

            AnyObjectId anyObjectId = head.getObjectId();
            if(anyObjectId == null)
                return rootNode;
            RevCommit commit = walk.parseCommit(anyObjectId);
            RevTree tree = commit.getTree();
            System.out.println("Having tree: " + tree);
            TreeWalk treeWalk;
            treeWalk = new TreeWalk(repo);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);

            Node parent = rootNode;
            rootNode.setDepth(-1);

            while(treeWalk.next()){

                while (treeWalk.getDepth() <= parent.getDepth())
                    parent = parent.getParent();

                Node child = parent.addChild(new Node(treeWalk.getPathString()));
                child.setDepth(treeWalk.getDepth());
                if(treeWalk.isSubtree()) {
                    treeWalk.enterSubtree();
                    parent = child;
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return rootNode;
    }
}
