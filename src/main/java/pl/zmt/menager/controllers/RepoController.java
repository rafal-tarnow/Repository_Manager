package pl.zmt.menager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String newRepo(){
        return "newrepo";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveRepo(@RequestParam("add") String add, @RequestParam("index") String index, @RequestParam("name") String name , @RequestParam("description") String description, HttpServletRequest request){
        repoService.save(index,name,description);
        return "redirect:/repo/all";
    }

}
