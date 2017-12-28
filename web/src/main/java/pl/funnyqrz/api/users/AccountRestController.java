package pl.funnyqrz.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.funnyqrz.dto.UserDto;
import pl.funnyqrz.entities.account.User;
import pl.funnyqrz.messages.GenericMessage;
import pl.funnyqrz.messages.SystemMessage;
import pl.funnyqrz.services.account.UserService;

import javax.validation.Valid;

@RestController("/account")
public class AccountRestController {

    private UserService userService;

    @Autowired
    public AccountRestController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<GenericMessage> registerNewAccount(@Valid UserDto newUser) {
        userService.createNewAccount(newUser);
        return ResponseEntity.ok(new GenericMessage(SystemMessage.USER_CORRECTLY_REGISTER));
    }

}
