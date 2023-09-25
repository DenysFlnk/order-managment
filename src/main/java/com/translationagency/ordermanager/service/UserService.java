package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.User;
import com.translationagency.ordermanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.translationagency.ordermanager.util.UserUtil.*;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found"));
    }

    public void create(User user) {
        setSHA1Encoder(user);
        userRepository.save(user);
    }

    public void update(User user) {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            User userWithPassword = userRepository.findById(user.id())
                    .orElseThrow(() -> new NoSuchElementException("Not found"));
            user.setPassword(userWithPassword.getPassword());
        } else {
            setSHA1Encoder(user);
        }
        userRepository.save(user);
    }

    public void delete(int id) {
        User delete = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found"));
        userRepository.delete(delete);
    }

    public void enable(int id, boolean isEnabled) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found"));
        user.setEnabled(isEnabled);
        userRepository.save(user);
    }
}
