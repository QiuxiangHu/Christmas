package com.amily.Christmas.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal) {  	
        return "index";
    }
    
    @RequestMapping(value = "/star", method = RequestMethod.GET)
    public String star(Principal principal) {  	
        return "star";
    }
}
