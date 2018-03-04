package pl.funnyqrz.rest.users;

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
@RequestMapping("/account")
public class UserRestService {

    private UserService userService;

    @Autowired
    public UserRestService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericMessage<String>> registerNewAccount(@RequestBody @Valid UserDto newUser) {
        userService.createNewAccount(newUser);
        return ResponseEntity.ok(new GenericMessage<String>(SystemMessage.USER_CORRECTLY_REGISTER));
    }


}
