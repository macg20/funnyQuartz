package pl.funnyqrz.services.account;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.funnyqrz.entities.account.Role;
import pl.funnyqrz.entities.account.User;
import pl.funnyqrz.exceptions.UserAlreadyRegisterException;
import pl.funnyqrz.mapper.GenericMapper;
import pl.funnyqrz.mapper.dto.UserDto;
import pl.funnyqrz.repositories.UserRepository;
import pl.funnyqrz.services.AbstractService;

import java.util.Set;

import static pl.funnyqrz.messages.SystemMessage.USER_ALREADY_EXISTS;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    private final static String ROLE_USER = "ROLE_USER";

    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;
    private GenericMapper<UserDto,User> mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder, @Qualifier("userMapper") GenericMapper mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public User createNewAccount(UserDto userDto) {
        isExists(userDto.getEmail());
        return userRepository.save(mapDtoToUser(userDto));
    }

    @Override
    public Set<String> findAllEmails() {
        return userRepository.findAllEmails();
    }

    private void isExists(String email) {
        userRepository.ifExistsUserEmail(email).stream().findFirst().ifPresent(user -> {
            throw new UserAlreadyRegisterException(USER_ALREADY_EXISTS);
        });
    }

    private User mapDtoToUser(UserDto userDto) {
        User user = mapper.mapToEntity(userDto);
        user.setPassword(encryptPassword(user.getPassword()));
        user.getRoles().add(getUserRole());
        return user;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Role getUserRole() {
        return roleService.findRoleByName(ROLE_USER);
    }
}
