import com.example.talent_api.TalentApiApplication;
import com.example.talent_api.controller.AuthenticationController;
import com.example.talent_api.model.User;
import com.example.talent_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TalentApiApplication.class)
class AuthenticationControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void testLoginSuccess() {
        // Mock user object
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("testPassword");
        mockUser.setType("user");

        // Set up mock behavior for UserService
        when(userService.findUserByUsername(anyString(), anyString())).thenReturn(mockUser);

        // Set up login credentials
        Map<String, String> loginCredentials = new HashMap<>();
        loginCredentials.put("username", "testUser");
        loginCredentials.put("password", "testPassword");

        // Call the login method
        ResponseEntity<?> response = authenticationController.login(loginCredentials);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockUser);
    }

    @Test
    void testLoginFailure() {
        // Set up mock behavior for UserService to return null when the user is not found
        when(userService.findUserByUsername(anyString(), anyString())).thenReturn(null);

        // Set up login credentials
        Map<String, String> loginCredentials = new HashMap<>();
        loginCredentials.put("username", "testUser");
        loginCredentials.put("password", "wrongPassword");

        // Call the login method
        ResponseEntity<?> response = authenticationController.login(loginCredentials);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void testRegistrationSuccess() {
        // Mock new user object
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("newPassword");
        newUser.setType("admin");

        // Set up mock behavior for UserService
        when(userService.register(anyString(), anyString(), anyString())).thenReturn(newUser);

        // Set up registration credentials
        Map<String, String> registrationCredentials = new HashMap<>();
        registrationCredentials.put("username", "newUser");
        registrationCredentials.put("password", "newPassword");
        registrationCredentials.put("type", "admin");

        // Call the registration method
        ResponseEntity<?> response = authenticationController.registration(registrationCredentials);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(newUser);
    }

    @Test
    void testRegistrationFailure() {
        // Set up mock behavior for UserService to return null when registration fails
        when(userService.register(anyString(), anyString(), anyString())).thenReturn(null);

        // Set up registration credentials
        Map<String, String> registrationCredentials = new HashMap<>();
        registrationCredentials.put("username", "existingUser");
        registrationCredentials.put("password", "password");
        registrationCredentials.put("type", "user");

        // Call the registration method
        ResponseEntity<?> response = authenticationController.registration(registrationCredentials);


        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("username already exists.");
    }
}


