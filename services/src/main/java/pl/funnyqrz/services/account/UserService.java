package pl.funnyqrz.services.account;

import pl.funnyqrz.entities.account.UserEntity;
import pl.funnyqrz.mappers.dto.UserDto;

import java.util.Set;

public interface UserService {

    UserEntity createNewAccount(UserDto userDto);

    Set<String> findAllEmails();

}
