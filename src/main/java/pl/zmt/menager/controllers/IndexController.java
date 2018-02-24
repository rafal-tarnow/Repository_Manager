package pl.zmt.menager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/index")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String showMainPage(){
        return "index";
    }
}
