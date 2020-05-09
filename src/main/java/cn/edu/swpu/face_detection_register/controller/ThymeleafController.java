package cn.edu.swpu.face_detection_register.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class ThymeleafController {

    @GetMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

    @GetMapping(value = "/home")
    public String home(){
        return "home";
    }

    @GetMapping("/404")
    public String error404(HttpServletRequest request,
                           Model model){
        String username = (String) request.getSession().getAttribute("username");
        model.addAttribute("username",username);
        return "404";
    }

    @GetMapping("/403")
    public String error403(HttpServletRequest request,
                           Model model){
        String username = (String) request.getSession().getAttribute("username");
        model.addAttribute("username",username);
        return "403";
    }
}
