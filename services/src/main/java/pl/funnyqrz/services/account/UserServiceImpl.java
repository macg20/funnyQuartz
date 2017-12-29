package pl.funnyqrz.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.dto.UserDto;
import pl.funnyqrz.entities.account.User;
import pl.funnyqrz.exceptions.UserAlreadyRegisterException;
import pl.funnyqrz.repositories.UserRepository;
import pl.funnyqrz.services.AbstractService;

import java.util.Set;

import static pl.funnyqrz.messages.SystemMessage.USER_ALREADY_EXISTS;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User createNewAccount(UserDto userDto) {
        isExsist(userDto.getEmail());
        return userRepository.save(mapDtoToUser(userDto));
    }

    @Override
    public Set<String> findAllUserEmail() {
        return userRepository.findAllEmails();
    }

    private void isExsist(String email) {
        userRepository.ifExistsUserEmail(email).stream().findFirst().ifPresent(user -> {
            throw new UserAlreadyRegisterException(USER_ALREADY_EXISTS);});
    }

    private User mapDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(encryptPassword(userDto.getPassword()));
        user.setEnabled(true);
        return user;
    }

    private UserDto mapUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEnabled(user.isEnabled());
        return dto;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
