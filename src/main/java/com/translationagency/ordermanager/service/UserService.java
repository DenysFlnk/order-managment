package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.User;
import com.translationagency.ordermanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.translationagency.ordermanager.util.UserUtil.*;
import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }

    public void create(User user) {
        checkNew(user);
        setSHA1Encoder(user);
        userRepository.save(user);
    }

    public void update(User user, int id) {
        if (user.getPassword() == null) {
            User userWithPassword = checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
            user.setPassword(userWithPassword.getPassword());
        } else {
            checkUserPassword(user);
            setSHA1Encoder(user);
        }
        userRepository.save(user);
    }

    public void delete(int id) {
        User delete = checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
        userRepository.delete(delete);
    }

    public void enable(int id, boolean isEnabled) {
        User user = checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
        user.setEnabled(isEnabled);
        userRepository.save(user);
    }
}
