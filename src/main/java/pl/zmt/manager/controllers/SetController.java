package pl.zmt.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.zmt.manager.services.SetService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/set")
public class SetController {

    @Autowired
    private SetService setService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllSets(HttpServletRequest request){
        request.setAttribute("sets", setService.findAll());
        return "sets";
    }
}
