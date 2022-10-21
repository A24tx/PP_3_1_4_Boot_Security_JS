package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private BCryptPasswordEncoder bcrypt;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder bcrypt) {
        this.userRepo = userRepo;
        this.bcrypt = bcrypt;
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional
    public void saveUser(User u) {
        u.setPassword(bcrypt.encode(u.getPassword()));
        userRepo.save(u);
        System.out.println("-- DEBUG: saved user " + u.getUsername() + " " + u.getName());
    }

    @Override
    @Transactional
    public void updateUser(User u) {
        Optional<User> fromDB = userRepo.findById(u.getId());
        if (fromDB.isPresent()) {
            User userFromDB = fromDB.get();
            userFromDB.setUsername(u.getUsername());
            userFromDB.setName(u.getName());
            userFromDB.setPassword(bcrypt.encode(u.getPassword()));
            Set<Role> newRoles = new HashSet<>();

            for (Role role : u.getRoles()) {
                Optional<Role> roleFromDB = roleRepo.findById(role.getId());
                if (roleFromDB.isPresent()) {
                    newRoles.add(roleFromDB.get());
                }
            }

            userFromDB.setRoles(newRoles);
        }
    }



    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(new User());
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        if (userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepo.findByUsername(username);
        if (userDetails == null) {
            return new User();
        }
        return userDetails;
    }


}
