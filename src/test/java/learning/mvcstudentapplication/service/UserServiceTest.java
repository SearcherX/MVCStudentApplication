package learning.mvcstudentapplication.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()

class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void addUser() {
        userService.addUser("test2", "123");
        userService.addUser("test3", "1234");
        userService.addUser("test4", "12345");
    }
}