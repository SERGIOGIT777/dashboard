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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adminDashboard")
public class AdminController {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private CabinetRepository cabinetRepository;

    @GetMapping("/errorsPassword")
    public ModelAndView errorPage(){
        ModelAndView mav = new ModelAndView("dashboard/errorsPassword");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        return mav;
    }


    //-------------------------------------Главная страница---------------------------------------------

    @GetMapping("/dashboard")
    public ModelAndView indexPage(){
        ModelAndView mav = new ModelAndView("dashboard/index");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("countUsers", peopleRepository.count());
        mav.addObject("countIndex", cabinetRepository.count());
        mav.addObject("listPeoples", peopleRepository.findAll());
        mav.addObject("listCabinet", cabinetRepository.findAll());
        mav.addObject("finder", new Peoples());
        return mav;
    }

    //------------------------------Удаление записей---------------------

    @DeleteMapping("/deletePeoples")
    public String deletePeoples(@RequestParam Long id) {
        peopleRepository.deleteById(id);
        return "redirect:/adminDashboard/dashboard";
    }

    //------------------------------Поиск пользователей---------------------------

    @GetMapping("/findPeople")
    public ModelAndView findPeople(@RequestParam String name) {
        ModelAndView mav = new ModelAndView("dashboard/index");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("finder", new Peoples());
        mav.addObject("listPeoples", peopleRepository.findNameAll(name));
        mav.addObject("listCabinet", cabinetRepository.findName(name));
        return mav;
    }

    //------------------------------Обновление записей---------------------

    @GetMapping("/updatePeoples")
    public ModelAndView updatePeoplesForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("dashboard/blankUpdatePeoples");
        mav.addObject("updatesPeoples", peopleRepository.getId(id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        return mav;
    }

    @Transactional
    @PostMapping("/updatePeoples")
    public String updatePeoples(Long id, String login, String password, String authority,
                              String name, String email, Integer age) {
        peopleRepository.updateUsers(id, login, password, authority, name, email, age);
        return "redirect:/adminDashboard/dashboard";
    }

    //------------------------------Страница пользователя - обновление пароля---------------------


    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView("dashboard/pages-profile");
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
    @PostMapping("/profile")
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
            return "redirect:/adminDashboard/errorsPassword";
        }

        return "redirect:/adminDashboard/dashboard";
    }

    //------------------------------Страница пользователя - обновление профиля---------------------

    @GetMapping("/updateUsers")
    public ModelAndView updateUsersForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("dashboard/blankUpdate");
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
        return "redirect:/adminDashboard/dashboard";
    }

    //------------------------------Страница сведений---------------------

    @GetMapping("/bookTable")
    public ModelAndView book(){
        ModelAndView mav = new ModelAndView("dashboard/table-basic");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("listBook", cabinetRepository.findAll());
        mav.addObject("finder", new Cabinet());
        return mav;
    }

    @Transactional
    @GetMapping("/addBookTable")
    public ModelAndView addBook(@RequestParam long id){
        ModelAndView mav = new ModelAndView("dashboard/addTable");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String users = user.getUsername();
        List<Peoples> peoples = peopleRepository.findByLogin(users);
        Peoples users1 = peopleRepository.getId(id);
        Cabinet cabinet = new Cabinet();
        cabinet.setPeoples(users1);
        mav.addObject("people", peoples.get(0).getName());
        mav.addObject("author", peoples.get(0).getAuthority());
        mav.addObject("addBook", cabinet);
        return mav;
    }

    @Transactional
    @PostMapping("/addBookTable")
    public String saveBook(@ModelAttribute("addBook") @Valid Cabinet cabinet,
                               BindingResult result,
                               @RequestParam("id_peoples") Peoples peoples) {

        if (result.hasErrors()) {
            return "dashboard/addTable";
        }

        cabinet.setPeoples(peoples);
        cabinetRepository.save(cabinet);

        return "redirect:/adminDashboard/bookTable";
    }

    //------------------------------Редактирование страницы сведений---------------------


    @GetMapping("/updateBook")
    public ModelAndView updateBookForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("dashboard/update-table-basic");
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
        return "redirect:/adminDashboard/bookTable";
    }

    //------------------------------Удаление сведений---------------------

    @DeleteMapping("/deleteBook")
    public String deleteBooks(@RequestParam Long id) {
        cabinetRepository.deleteById(id);
        return "redirect:/adminDashboard/bookTable";
    }

    //------------------------------Поиск сведений---------------------------

    @GetMapping("/findBook")
    public ModelAndView findBook(@RequestParam String position) {
        ModelAndView mav = new ModelAndView("dashboard/table-basic");
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
