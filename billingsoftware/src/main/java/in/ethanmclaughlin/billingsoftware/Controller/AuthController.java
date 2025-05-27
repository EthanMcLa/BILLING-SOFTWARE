package in.ethanmclaughlin.billingsoftware.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import in.ethanmclaughlin.billingsoftware.io.AuthRequest;
import in.ethanmclaughlin.billingsoftware.io.AuthResponse;
import in.ethanmclaughlin.billingsoftware.service.UserService;
import in.ethanmclaughlin.billingsoftware.service.impl.AppUserDetailsService;
import in.ethanmclaughlin.billingsoftware.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationmanger;
    private final AppUserDetailsService appUserDetailsService;
    private final UserService userService;

    private final JwtUtil jwtuUtil;

    @PostMapping({"/login"})
    public AuthResponse login(@RequestBody AuthRequest request) throws Exception {
            System.out.println("Login attempt with email: " + request.getEmail() + ", password: " + request.getPassword());
            
            // Try direct password verification first for debugging
            try {
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getEmail());
                boolean matches = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());
                System.out.println("Direct password verification: " + (matches ? "SUCCESS" : "FAILED"));
                
                // Continue with normal authentication
                authenticate(request.getEmail(), request.getPassword());
                final String jwtToken = jwtuUtil.generateToken(userDetails);
                String role = userService.getUserRole(request.getEmail());
                return new AuthResponse(request.getEmail(), role, jwtToken);
            } catch (Exception e) {
                System.out.println("Authentication error: " + e.getMessage());
                throw e;
            }
    }
        
    private void authenticate(String email, String password) throws Exception {
        try {
            System.out.println("Attempting authentication for: " + email);
            authenticationmanger.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            System.out.println("Authentication successful");
        } catch (DisabledException e) {
            System.out.println("User disabled: " + e.getMessage());
            throw new Exception("User Disabled");
        } catch(BadCredentialsException e) {
            System.out.println("Bad credentials: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password is incorrect");
        } catch (Exception e) {
            System.out.println("Unexpected authentication error: " + e.getClass().getName() + " - " + e.getMessage());
            throw e;
        }
    }

    @PostMapping({"/encode", "/api/v1.0/encode"})
    public String encodedPassword(@RequestBody Map<String, String> request ) {
        return passwordEncoder.encode(request.get("password"));
    }
    

}