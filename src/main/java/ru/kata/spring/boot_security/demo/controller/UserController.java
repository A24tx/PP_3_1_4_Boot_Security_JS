package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@RestController
public class UserController {

    private UserServiceImpl myUserService;
    private RoleServiceImpl myRoleService;

    public UserController(UserServiceImpl myUserService, RoleServiceImpl myRoleService) {
        this.myUserService = myUserService;
        this.myRoleService = myRoleService;
    }


    @RequestMapping("/user")
    public ModelAndView showUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("/currentUser")
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
