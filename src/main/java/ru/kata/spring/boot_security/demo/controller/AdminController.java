package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@RestController
public class AdminController {
    private UserServiceImpl myUserService;
    private RoleServiceImpl myRoleService;

    public AdminController(UserServiceImpl myUserService, RoleServiceImpl myRoleService) {
        this.myUserService = myUserService;
        this.myRoleService = myRoleService;
    }

    @RequestMapping("/admin")
    public ModelAndView showAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }


    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(myUserService.getUsers(), HttpStatus.OK);
    }


    @GetMapping("/admin/getOne/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable long id) {
        return new ResponseEntity<>(myUserService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/admin/add")
    public ResponseEntity newUser(@RequestBody List<User> payload) {
        myUserService.saveUser(payload.get(0));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/admin/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        myUserService.removeUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/admin/update")
    public ResponseEntity updateUser(@RequestBody List<User> payload) {
        myUserService.updateUser(payload.get(0));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/admin/getAllRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(myRoleService.getAllRoles(), HttpStatus.OK);
    }


}
