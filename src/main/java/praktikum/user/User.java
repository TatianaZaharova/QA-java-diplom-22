package praktikum.user;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;
    private String email;
    private String password;


    // Метод генерирует рандомные данные для создания пользователя
    public static User random() {
        Faker faker = new Faker();
        String randomName = faker.name().firstName();
        String email = randomName + RandomStringUtils.randomAlphanumeric(5) + "@mail.com";
        String password = "123456";
        return new User(randomName, email, password);
    }

}