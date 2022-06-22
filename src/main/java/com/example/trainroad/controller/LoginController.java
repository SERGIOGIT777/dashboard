package com.example.trainroad.controller;

import com.example.trainroad.entities.Peoples;
import com.example.trainroad.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping({"/", "/login"})
public class LoginController {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping
    public ModelAndView loginPage(){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("peoples", new Peoples());
        mav.addObject("authority", "ROLE_USER");
        return mav;
    }

    @GetMapping("/saveUsers")
    public ModelAndView addUserForm(Model model) {
        ModelAndView mav = new ModelAndView("index");
        model.addAttribute("authority", "ROLE_USER");
        mav.addObject("peoples", new Peoples());
        return mav;
    }

    @PostMapping("/saveUsers")
    public String saveUsers(@ModelAttribute("peoples") @Valid Peoples peoples,
                            BindingResult result, Model model) {

        model.addAttribute("authority", "ROLE_USER");

        if (result.hasErrors()) {
            return "index";
        }

        if (!peoples.getPassword().equals(peoples.getConfirm_password())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "index";
        }

        var list = peopleRepository.findByLogin(peoples.getLogin());

        if (list.size() > 0) {
            model.addAttribute("loginError", "Пользователь с таким логином уже существует");
            return "index";
        } else if (list.isEmpty()){
            peoples.setPassword(passwordEncoder.encode(peoples.getPassword()));
            peopleRepository.save(peoples);
            model.addAttribute("peoples", peopleRepository.findAll());
        }
        return "redirect:/login";
    }
}
