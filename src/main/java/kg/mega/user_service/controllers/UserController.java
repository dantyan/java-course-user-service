package kg.mega.user_service.controllers;

import kg.mega.user_service.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    // Используем HashMap для быстрого доступа по ID
    private Map<Long, User> users = new HashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        Long id = counter.incrementAndGet();
        user.setId(id);
        users.put(id, user); // Добавляем в словарь
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        // Превращаем значения словаря обратно в список для ответа
        return new ArrayList<>(users.values());
    }

    @GetMapping("/user/{id}")
    public User getById(@PathVariable Long id) {
        // Теперь поиск работает моментально без стримов
        return users.get(id);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User currentUser = users.get(id);
        if (currentUser != null) {
            currentUser.setAge(user.getAge());
            currentUser.setEmail(user.getEmail());
            currentUser.setName(user.getName());
        }
        return currentUser;
    }

    @DeleteMapping("/user/{id}")
    public User deleteById(@PathVariable Long id) {
        // Метод remove возвращает удаленный объект
        return users.remove(id);
    }

    @GetMapping("/users/filter")
    public List<User> getUsersOlderThan(@RequestParam int age) {
        // Для фильтрации по полям (не по ID) всё равно используем стримы
        return users.values()
                .stream()
                .filter(x -> x.getAge() > age)
                .toList();
    }
}