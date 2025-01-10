package praktikum.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class UserParameterizedTest {

    UserChecks checks = new UserChecks();
    UserClient client = new UserClient();

    private String name;
    private String email;
    private String password;

    public UserParameterizedTest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }




    @Parameterized.Parameters()
    public static Object[][] createUserData() {
        Faker faker = new Faker();
        return new Object[][]{
                {null, faker.name().firstName() + RandomStringUtils.randomAlphanumeric(5) + "@mail.com", "123456"},
                {faker.name().firstName(), null, "123456"},
                {faker.name().firstName(), faker.name().firstName() + RandomStringUtils.randomAlphanumeric(5) + "@mail.com", null},
        };
    }

    @Test
    @DisplayName("Нельзя создать пользователя, если не заполнено одно из обязательных полей")
    public void userWithoutField() {
        Allure.parameter("name", name);
        Allure.parameter("email", email);
        Allure.parameter("password", password);
        User user = new User(name, email, password);
        ValidatableResponse createResponse = client.createUser(user);
        checks.checkCreateWithoutFields(createResponse);
    }
}