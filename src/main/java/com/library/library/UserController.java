package com.library.library;

import com.library.library.models.User;
import com.library.library.services.Interface.UserServiceInterface;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController {

    private UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping("get")
    public List<User> getUser() {
        return userService.getRepository().findAll();
    }

    @GetMapping("get/{userId}")
    public Optional<User> getUser(@PathVariable Long userId) {
        return userService.getRepository().findById(userId);
    }

    @PostMapping("store")
    public String store(@Valid @RequestBody User user) {
        try {
            userService.save(user);
        } catch (RuntimeException $ex) {
            return $ex.getMessage();
        }

        return "User stored with success!";
    }

    @PutMapping("update")
    public String update(@Valid @RequestBody User user) {
        try {
            userService.getRepository().findById(user.getUserId()).orElseThrow(() -> new Exception("User not found!"));
            userService.save(user);
        } catch (Exception $ex) {
            return $ex.getMessage();
        }

        return "User updated with success!";
    }

    @DeleteMapping("delete/{userId}")
    public String delete(@PathVariable Long userId) throws Exception {
        try {
            User user = userService.getRepository().findById(userId).orElseThrow(() -> new Exception("User not found!"));
            userService.getRepository().delete(user);
        } catch (Exception $ex) {
            return $ex.getMessage();
        }

        return "User deleted with success!";
    }

}
