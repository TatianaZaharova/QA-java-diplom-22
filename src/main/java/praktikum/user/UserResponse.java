package praktikum.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

}