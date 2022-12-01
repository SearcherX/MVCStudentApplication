package learning.mvcstudentapplication.service;

import learning.mvcstudentapplication.db.entity.Role;
import learning.mvcstudentapplication.db.entity.User;
import learning.mvcstudentapplication.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

// сервис для работы с таблицей пользователей
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;  // репоизтоирий
    @Autowired
    private PasswordEncoder encoder;        // кодировщик
    // Добавление нового пользователя
    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        password = encoder.encode(password);    // захэшировать пароль
        user.setPassword(password); // пароль надо хэшировать
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);   // сохранил пользователя
    }

    public void setAdmin(String username) {
        User user = getUserByUsername(username);
        Set<Role> roles = user.getRoles();
        roles.add(Role.ADMIN);
        userRepository.save(user);
    }

    // метод получения объекта пользователя по username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
