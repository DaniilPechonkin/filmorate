import filmorate.model.User;
import filmorate.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void addUser_ValidUser_ReturnsCreatedResponse() {
        User user = new User();
        user.setLogin("validUser");
        user.setEmail("user@example.com");
        user.setName("example");
        user.setBirthday(LocalDateTime.now());

        ResponseEntity<User> response = userController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("validUser", response.getBody().getLogin());
    }

    @Test
    void addUser_InvalidUser_ReturnsBadRequest() {
        User user = new User();
        user.setLogin("");

        ResponseEntity<User> response = userController.addUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateUser_ValidUser_ReturnsOkResponse() {
        User user = new User();
        user.setLogin("firstUser");
        user.setEmail("user1@example.com");
        userController.addUser(user);

        User updatedUser = new User();
        updatedUser.setLogin("updatedUser");
        updatedUser.setEmail("updated@example.com");

        ResponseEntity<User> response = userController.updateUser(0, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("updatedUser", response.getBody().getLogin());
    }

    @Test
    void updateUser_NonExistentUser_ReturnsNotFound() {
        User updatedUser = new User();
        updatedUser.setLogin("updatedUser");

        ResponseEntity<User> response = userController.updateUser(0, updatedUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        User user1 = new User();
        user1.setLogin("user1");
        userController.addUser(user1);

        User user2 = new User();
        user2.setLogin("user2");
        userController.addUser(user2);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}