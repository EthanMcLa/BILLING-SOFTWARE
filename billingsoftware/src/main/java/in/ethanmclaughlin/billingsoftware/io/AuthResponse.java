package in.ethanmclaughlin.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AuthResponse {

        private final String email;
        private final String role;
        private final String token;

}
