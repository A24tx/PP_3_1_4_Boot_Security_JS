package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    public void saveUser(User u);

    public List<User> getUsers();

    public void removeUser(Long id);

    public User getUserById(Long id);

    public void updateUser(User u);

    public void updateUserFromPayload(List<User> payload);


}
