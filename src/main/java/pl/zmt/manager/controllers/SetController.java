package pl.zmt.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zmt.manager.entities.Repo;
import pl.zmt.manager.entities.Set;
import pl.zmt.manager.services.CompositionService;
import pl.zmt.manager.services.SetService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/set")
public class SetController {

    @Autowired
    private SetService setService;
    @Autowired
    private CompositionService compositionService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllSets(HttpServletRequest request){
        Collection<Set> sets = setService.findAll();
        request.setAttribute("sets", sets);
        request.setAttribute("treeSet", compositionService.returnTree(sets));
        return "sets";
    }

    @RequestMapping(value = "/new",method = RequestMethod.GET)
    public String newRepo(){
        return "newset";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveRepo(@RequestParam("name") String name , @RequestParam("description") String description, HttpServletRequest request){
        if(setService.createRepository(name,description)){
            return "redirect:/set/all";
        }else{
            request.setAttribute("used",true);
            return "newset";
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String repoDetailsIndex(@PathVariable String name, HttpServletRequest request) {
        request.setAttribute("set",setService.findByName(name));
        List<Repo> repositories =  compositionService.findAllBySetName(name);
        request.setAttribute("repositories",repositories);
        request.setAttribute("all_repos", compositionService.findAllUnusedRepo(repositories));
        return "setdetails";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public String updateRepo(@PathVariable String name, @RequestParam("description") String description){
        setService.updateRepo(name,description);
        return "redirect:/set/{name}";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public String deleteRepo(@PathVariable String name, @RequestParam("id_repo") Long id_repo, @RequestParam("id_set") Integer id_set){
        compositionService.delete(id_set,id_repo);
        return "redirect:/set/{name}";
    }

    @RequestMapping(value = "/{name}/{id_set}", method = RequestMethod.POST)
    public String addRepoToSet(@PathVariable String name, @PathVariable Integer id_set, @RequestParam("id_repo") Long id_repo){
        compositionService.add(id_set, id_repo);
        return "redirect:/set/{name}";
    }

}
