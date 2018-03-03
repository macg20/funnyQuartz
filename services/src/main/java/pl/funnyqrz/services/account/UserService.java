package pl.funnyqrz.services.account;

import pl.funnyqrz.entities.account.User;
import pl.funnyqrz.mappers.dto.UserDto;

import java.util.Set;

public interface UserService {

    User createNewAccount(UserDto userDto);

    Set<String> findAllEmails();

}
