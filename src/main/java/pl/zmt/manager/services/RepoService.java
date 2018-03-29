package pl.zmt.manager.services;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zmt.manager.Node;
import pl.zmt.manager.entity.Repo;
import pl.zmt.manager.repository.RepoRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.System.out;
import static pl.zmt.manager.NodeType.*;

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
                out.println("Directory is created!");
                Git git = Git.init().setDirectory(file).setBare( true ).call();
                if(git.getRepository().isBare()){
                    out.println("Repository is initialized!");
                    save(index,name,description);
                }else {
                    out.println("Failed to initialized repository!");
                }
            } else {
                out.println("Failed to create directory!");
            }
        }
    }

    public ArrayList<String> returnTagsList(String index){
        String path = "/srv/repos/git/" + index;

        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        try {
            Repository repo = builder.setGitDir(new File(path)).setMustExist(true).build();

            Map<String, Ref> tagMap = repo.getTags();
            ArrayList<String> tags = new ArrayList<>();
            tagMap.forEach((key, value) -> {
                tags.add(key);
            });
            return tags;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
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
            out.println("Having tree: " + tree);
            TreeWalk treeWalk;
            treeWalk = new TreeWalk(repo);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);

            Node parent = rootNode;
            rootNode.setDepth(-1);
            rootNode.setType(DIRECTORY);

            while(treeWalk.next()){

                while (treeWalk.getDepth() <= parent.getDepth())
                    parent = parent.getParent();

                Node child = parent.addChild(new Node(treeWalk.getNameString(), treeWalk.getPathString()));
                child.setDepth(treeWalk.getDepth());
                if(treeWalk.isSubtree()) {
                    child.setType(DIRECTORY);
                    treeWalk.enterSubtree();
                    parent = child;
                }else {
                    child.setType(FILE);
                }

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return rootNode;
    }

    public String returnContentFile(String index, String fileName, String path) {

        String folder = "/srv/repos/git/" + index;

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        String content = "";

        try {
            Repository repo = builder.setGitDir(new File(folder)).setMustExist(true).build();
            Ref head = repo.getRef("HEAD");
            RevWalk walk = new RevWalk(repo);

            AnyObjectId anyObjectId = head.getObjectId();
            if (anyObjectId == null)
                return content;

            RevCommit commit = walk.parseCommit(anyObjectId);
            RevTree tree = commit.getTree();
            System.out.println("Having tree: " + tree);

            TreeWalk treeWalk = new TreeWalk(repo);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);

            treeWalk.setFilter(PathFilter.create(path+'/'+fileName));
            if (!treeWalk.next()) {
                System.out.println("Did not find expected file:" + fileName);
                return content;
            }

            ObjectId objectId = treeWalk.getObjectId(0);
            ObjectLoader loader = repo.open(objectId);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            loader.copyTo(os);
            content = new String(os.toByteArray(),"UTF-8");
            return content;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public Optional<Repo> findById(Long id) {
        return repoRepository.findById(id);
    }
}
