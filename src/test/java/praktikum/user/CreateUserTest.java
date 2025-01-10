package praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;


public class CreateUserTest {

    private UserChecks checks = new UserChecks();
    private UserClient client = new UserClient();

    String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            // Проверяем код и тело ответа после удаления
            ValidatableResponse deleteResponse = client.deleteUser(accessToken);
            checks.checkDeleted(deleteResponse);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void uniqueUser() {
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = checks.checkCreateUser(createResponse);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void doubleUser() {
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = checks.checkCreateUser(createResponse);
        ValidatableResponse doubleCreateResponse = client.createUser(user);
        checks.checkDoubleUser(doubleCreateResponse);
    }



}