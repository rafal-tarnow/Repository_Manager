package pl.zmt.menager.controllers;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zmt.menager.services.RepoService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/repo")
public class RepoController {

    @Autowired
    private RepoService repoService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllRepos(HttpServletRequest request){
        request.setAttribute("repos", repoService.findAll());
        return "repos";
    }

    @RequestMapping(value = "/new",method = RequestMethod.GET)
    public String newRepo(HttpServletRequest request){
        request.setAttribute("options", repoService.returnOptionsList());
        return "newrepo";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveRepo(@RequestParam("add") String add, @RequestParam("option") String option, @RequestParam("name") String name , @RequestParam("description") String description, HttpServletRequest request) throws GitAPIException {
        repoService.createRepository(option,name,description);
        return "redirect:/repo/all";
    }

    @RequestMapping(value = "/{index}", method = RequestMethod.GET)
    public String repoDetailsIndex(@PathVariable String index, HttpServletRequest request) {
        request.setAttribute("repo", repoService.findByIndex(index));
        request.setAttribute("node",repoService.returnTreeNode(index));
        request.setAttribute("tags",repoService.returnTagsList(index));
        return "repodetails";
    }

    @RequestMapping(value = "/{index}", method = RequestMethod.PUT)
    public String updateRepo(@PathVariable String index, @RequestParam("description") String description, HttpServletRequest request){
        repoService.updateRepo(index,description);
        request.setAttribute("repo", repoService.findByIndex(index));
        return "redirect:/repo/{index}";
    }
}
