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
      return userRepository.save(mapToUser(userDto));
    }

    private void isExsist(String email) {
        userRepository.findByEmail(email).orElseThrow(() -> new UserAlreadyRegisterException(USER_ALREADY_EXISTS));

    }

    private User mapToUser(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(encryptPassword(userDto.getPassword()));
        user.setEnabled(true);
        return user;
    }

    private String encryptPassword(String password) {
       return passwordEncoder.encode(password);
    }
}
