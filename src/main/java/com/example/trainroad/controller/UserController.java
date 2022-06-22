package com.example.trainroad.controller;

import com.example.trainroad.entities.Cabinet;
import com.example.trainroad.entities.Peoples;
import com.example.trainroad.repository.CabinetRepository;
import com.example.trainroad.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/userDashboard")
public class UserController {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private CabinetRepository cabinetRepository;

    @GetMapping("/errorsPassword")
    public ModelAndView errorPage(){
        ModelAndView mav = new ModelAndView("dashboardUser/errorsPassword");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        return mav;
    }

    //------------------------------Страница пользователя - обновление пароля---------------------

    @GetMapping("/profile")
    public ModelAndView profile(){
        ModelAndView mav = new ModelAndView("dashboardUser/pages-profile");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("update", peopleRepository.getId(peoples.get(0).getId()));
        mav.addObject("id", peoples.get(0).getId());
        mav.addObject("login", peoples.get(0).getLogin());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("name", peoples.get(0).getName());
        mav.addObject("age", peoples.get(0).getAge());
        mav.addObject("email", peoples.get(0).getEmail());
        mav.addObject("oldPassword", peoples.get(0).getPassword());
        return mav;
    }


    @GetMapping("/updatePassword")
    public ModelAndView updatePassword() {
        ModelAndView mav = new ModelAndView("dashboardUser/pages-profile");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("update", peopleRepository.getId(peoples.get(0).getId()));
        mav.addObject("id", peoples.get(0).getId());
        mav.addObject("login", peoples.get(0).getLogin());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("name", peoples.get(0).getName());
        mav.addObject("age", peoples.get(0).getAge());
        mav.addObject("email", peoples.get(0).getEmail());
        mav.addObject("oldPassword", peoples.get(0).getPassword());
        return mav;
    }

    @Transactional
    @PostMapping("/updatePassword")
    public String updatePassword(Long id, String password,
                                 @RequestParam("oldPassword") String oldPassword) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordNew = passwordEncoder.encode(password);
        String users = user.getUsername();
        List<Peoples> users1 = peopleRepository.findByLogin(users);
        if (passwordEncoder.matches(oldPassword, users1.get(0).getPassword())) {
            peopleRepository.updatePassword(id, passwordNew);
        } else {
            return "redirect:/userDashboard/errorsPassword";
        }

        return "redirect:/userDashboard/profile";
    }

    //------------------------------Страница пользователя - обновление профиля---------------------

    @GetMapping("/updateUsers")
    public ModelAndView updateUsersForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("dashboardUser/blankUpdate");
        mav.addObject("updates", peopleRepository.getId(id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("name", peoples.get(0).getName());
        mav.addObject("age", peoples.get(0).getAge());
        mav.addObject("email", peoples.get(0).getEmail());
        return mav;
    }

    @Transactional
    @PostMapping("/updateUsers")
    public String updateUsers(Long id, String login, String password, String authority,
                              String name, String email, Integer age) {
        peopleRepository.updateUsers(id, login, password, authority, name, email, age);
        return "redirect:/userDashboard/profile";
    }

    //------------------------------Страница сведений---------------------

    @GetMapping("/bookTable")
    public ModelAndView book(){
        ModelAndView mav = new ModelAndView("dashboardUser/table-basic");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("listBook", cabinetRepository.findAll());
        mav.addObject("finder", new Cabinet());
        return mav;
    }

    //------------------------------Редактирование страницы сведений---------------------


    @GetMapping("/updateBook")
    public ModelAndView updateBookForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("dashboardUser/update-table-basic");
        mav.addObject("update", cabinetRepository.getId(id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("name", cabinetRepository.getId(id).getPeoples().getName());
        mav.addObject("email", cabinetRepository.getId(id).getPeoples().getEmail());
        mav.addObject("age", cabinetRepository.getId(id).getPeoples().getAge());
        List<String> positions = new ArrayList<>();
        positions.add("машинист");
        positions.add("помощник машиниста");
        positions.add("проводник");
        mav.addObject("positions", positions);
        mav.addObject("pos", cabinetRepository.getId(id).getPosition());
        List<String> ranks = new ArrayList<>();
        ranks.add("1");
        ranks.add("2");
        ranks.add("3");
        mav.addObject("ranks", ranks);
        mav.addObject("ran", cabinetRepository.getId(id).getRanks());
        return mav;
    }

    @Transactional
    @PostMapping("/updateBook")
    public String updateBooks(Long id, String position, Integer ranks) {
        cabinetRepository.updateBook(id, position, ranks);
        return "redirect:/userDashboard/bookTable";
    }

    //------------------------------Удаление сведений---------------------

    @DeleteMapping("/deleteBook")
    public String deleteBooks(@RequestParam Long id) {
        cabinetRepository.deleteById(id);
        return "redirect:/userDashboard/bookTable";
    }

    //------------------------------Поиск сведений---------------------------

    @GetMapping("/findBook")
    public ModelAndView findBook(@RequestParam String position) {
        ModelAndView mav = new ModelAndView("dashboardUser/table-basic");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("finder", new Cabinet());
        mav.addObject("listBook", cabinetRepository.findPosition(position));
        return mav;
    }

}
