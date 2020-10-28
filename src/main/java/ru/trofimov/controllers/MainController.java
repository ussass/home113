package ru.trofimov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.trofimov.config.AppConfig;
import ru.trofimov.utils.Utils;

@Controller
public class MainController {

    @GetMapping("/")
    public String showIndex(@RequestHeader("User-Agent") String userAgent, Model model){
        model.addAttribute("color", AppConfig.getColor());
        model.addAttribute("pageName", "Главная");
        return Utils.isMobile(userAgent) ? "indexMobile" : "index";
    }

    @GetMapping("error404")
    public String show404(@RequestHeader("User-Agent") String userAgent){
        return Utils.isMobile(userAgent) ? "error404Mobile" :  "error404";
    }

}
