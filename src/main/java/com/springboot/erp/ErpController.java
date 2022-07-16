package com.springboot.erp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErpController {
    
    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("dashboardMenuClass","active");
        return "dashboard";
    }
    
    @GetMapping({"/login"})
    public String login() {
        return "login";
    }
}
