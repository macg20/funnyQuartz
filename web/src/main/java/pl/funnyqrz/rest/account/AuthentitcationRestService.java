package pl.funnyqrz.rest.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import pl.funnyqrz.security.jwt.JwtHelper;
import pl.funnyqrz.security.jwt.JwtRequest;
import pl.funnyqrz.security.jwt.JwtResponse;
import pl.funnyqrz.services.account.UserService;

@RestController
@RequestMapping("/auth")
public class AuthentitcationRestService {

    private AuthenticationManager authenticationManager;
    private JwtHelper jwtHelper;
    private UserService userService;

    @Autowired
    public AuthentitcationRestService(AuthenticationManager authenticationManager, JwtHelper jwtHelper, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String token = jwtHelper.generateToken(user.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwtHelper.getExpiresIn(), token));
    }

    @GetMapping("/activate/{activate}")
    public ResponseEntity<?> activateAccount(@PathVariable("activate") String activateHash) {
        userService.activateAccount(activateHash);
        return ResponseEntity.ok(true);
    }


}
