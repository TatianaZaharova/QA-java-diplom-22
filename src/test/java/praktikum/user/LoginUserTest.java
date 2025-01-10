package praktikum.user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

public class LoginUserTest {

    UserClient client = new UserClient();
    UserChecks checks = new UserChecks();

    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            // Проверяем код и тело ответа после удаления
            ValidatableResponse deleteResponse = client.deleteUser(accessToken);
            checks.checkDeleted(deleteResponse);
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void successLogin() {
        var user = User.random();
        var userLogin = UserCredentials.fromUser(user);
        ValidatableResponse createResponse = client.createUser(user);
        // передаем accessToken на случай если тест упадет на запросе авторизации
        accessToken = checks.checkCreateUser(createResponse);
        ValidatableResponse loginResponse = client.loginUser(userLogin);
        accessToken = checks.checkSuccessLogin(loginResponse);
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неверным логином")
    public void incorrectLogin() {
        Faker faker = new Faker();
        var user = User.random();
        var userLogin = UserCredentials.fromUser(user);
        // устанавливаем неверный логин
        userLogin.setEmail(faker.name().firstName() + RandomStringUtils.randomAlphanumeric(5) + "@mail.com");
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = checks.checkCreateUser(createResponse);
        ValidatableResponse loginResponse = client.loginUser(userLogin);
        checks.checkLoginWithoutFields(loginResponse);
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неверным паролем")
    public void incorrectPassword() {
        Faker faker = new Faker();
        var user = User.random();
        var userLogin = UserCredentials.fromUser(user);
        // устанавливаем неверный пароль
        userLogin.setPassword(faker.date().toString());
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = checks.checkCreateUser(createResponse);
        ValidatableResponse loginResponse = client.loginUser(userLogin);
        checks.checkLoginWithoutFields(loginResponse);
    }

}