package praktikum.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentials {
    private String email;
    private String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials fromUser(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

}