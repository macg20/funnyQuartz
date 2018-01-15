package pl.funnyqrz.services.account;

import pl.funnyqrz.dto.UserDto;
import pl.funnyqrz.entities.account.User;

import java.util.Set;

public interface UserService {

    User createNewAccount(UserDto userDto);

    Set<String> findAllEmails();

}
