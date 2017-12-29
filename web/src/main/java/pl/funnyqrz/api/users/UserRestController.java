package pl.funnyqrz.api.users;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.funnyqrz.dto.UserDto;
import pl.funnyqrz.messages.GenericMessage;
import pl.funnyqrz.messages.SystemMessage;
import pl.funnyqrz.services.account.UserService;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/account")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping(value = "/register")
    public ResponseEntity<GenericMessage> registerNewAccount(@RequestBody @Valid UserDto newUser) {
        userService.createNewAccount(newUser);
        return ResponseEntity.ok(new GenericMessage<String>(Sets.newHashSet(Arrays.asList(SystemMessage.USER_CORRECTLY_REGISTER))));
    }


}
