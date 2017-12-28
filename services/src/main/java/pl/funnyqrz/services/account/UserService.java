package pl.funnyqrz.services.account;

import pl.funnyqrz.dto.UserDto;
import pl.funnyqrz.entities.account.User;

public interface UserService {

    User createNewAccount(UserDto userDto);
}
