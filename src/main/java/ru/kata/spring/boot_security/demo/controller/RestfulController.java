package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@RestController
public class RestfulController {
    private UserServiceImpl myUserService;
    private RoleServiceImpl myRoleService;

    public RestfulController(UserServiceImpl myUserService, RoleServiceImpl myRoleService) {
        this.myUserService = myUserService;
        this.myRoleService = myRoleService;
    }

    @RequestMapping("/admin")
    public ModelAndView showAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @RequestMapping("/user")
    public ModelAndView showUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("/admin/getAllUsers")
    public List<User> getAllUsers() {
        return myUserService.getUsers();
    }

    @GetMapping("/currentUser")
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/admin/getOne/{id}")
    public User getOneUser(@PathVariable long id) {
        return myUserService.getUserById(id);
    }

    @PostMapping("/admin/add")
    public void newUser(@RequestBody List<User> payload) {
        System.out.println("--- DEBUG attempting to save User: " + payload.get(0).getName() + " " + payload.get(0).getUsername());
        myUserService.saveUser(payload.get(0));
    }

    @RequestMapping("/admin/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        myUserService.removeUser(id);
    }

    @PostMapping("/admin/update")
    public void updateUser(@RequestBody List<User> payload) {
        myUserService.updateUser(payload.get(0));
    }

    @GetMapping("/admin/getAllRoles")
    public List<Role> getAllRoles() {
        return myRoleService.getAllRoles();
    }

//    @RequestMapping("/admin/saveUser")
//    public String saveUser(@ModelAttribute("user") User user) {
//        myUserService.saveUser(user);
//        return "redirect:/admin";
//    }
//
//
//
//    @GetMapping("/admin/delete/{id}")
//    public String deleteUser(@PathVariable(value = "id") long id, Model model) {
//        myUserService.removeUser(id);
//        return "redirect:/admin";
//    }
//
//
//    @PostMapping("/admin/updateUser")
//    public String updateUser(@ModelAttribute("user") User user) {
//        myUserService.updateUser(user);
//        return "redirect:/admin";
//    }


}
