package pl.funnyqrz.rest.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.funnyqrz.mappers.dto.UserDto;
import pl.funnyqrz.messages.GenericMessage;
import pl.funnyqrz.messages.SystemMessage;
import pl.funnyqrz.services.account.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterRestService {

    private UserService userService;

    @Autowired
    public RegisterRestService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericMessage<String>> registerNewAccount(@RequestBody @Valid UserDto newUser) {
        userService.createNewAccount(newUser);
        return ResponseEntity.ok(new GenericMessage<String>(SystemMessage.USER_CORRECTLY_REGISTER));
    }
}
