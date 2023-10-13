package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.User;
import com.translationagency.ordermanager.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = UserController.USER_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class UserController {

    public static final String USER_REST_URL = "rest-api/users";

    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        log.info("getAll");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("get {}", id);
        return userService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody User user) {
        log.info("create {}", user);
        userService.create(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody User user) {
        log.info("update id {}, data {}", id, user);
        assureIdConsistent(user, id);
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean isEnabled) {
        log.info("enable {}, isEnabled {}", id, isEnabled);
        userService.enable(id, isEnabled);
    }
}
