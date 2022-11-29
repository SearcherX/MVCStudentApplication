package learning.mvcstudentapplication.service;

import learning.mvcstudentapplication.db.entity.User;
import learning.mvcstudentapplication.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        userRepository.save(user);   // сохранил пользователя
    }

    // метод получения объекта пользователя по username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
