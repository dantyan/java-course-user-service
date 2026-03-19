package kg.mega.user_service.controllers;

import kg.mega.user_service.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private List<User> users = new ArrayList<>();
    private int counter = 0;

    @PostMapping("/user")
    public User saveUser(@RequestBody User user){
        Long id = (long) ++counter;
        user.setId(id);
        users.add(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return users;
    }

    @GetMapping("/user/{id}")
    public User getById(@PathVariable(name = "id") Long userId){
        User user = users
                .stream()
                .filter(x -> x.getId().equals(userId))
                .findFirst()
                .orElse(null);

        return user;

    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        User currentUser = getById(id);
        if (currentUser != null){
            currentUser.setAge(user.getAge());
            currentUser.setEmail(user.getEmail());
            currentUser.setName(user.getName());
        }
        return currentUser;
    }

    @DeleteMapping("/user/{id}")
    public User deleteById(@PathVariable Long id){
        User user = getById(id);
        if (user != null){
            users.remove(user);
        }
        return user;
    }

    @GetMapping("/users/filter")
    public List<User> getUsersOlderThen(@RequestParam int age){
        List<User> userList = users
                .stream()
                .filter(x->x.getAge()>age)
                .toList();
        return userList;
    }
}
