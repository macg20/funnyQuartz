package pl.funnyqrz.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.funnyqrz.entities.account.RoleEntity;
import pl.funnyqrz.entities.account.UserEntity;
import pl.funnyqrz.exceptions.UserAlreadyRegisterException;
import pl.funnyqrz.mappers.AbstractMapper;
import pl.funnyqrz.mappers.dto.UserDto;
import pl.funnyqrz.repositories.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.funnyqrz.messages.SystemMessage.USER_ALREADY_EXISTS;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final String ROLE_USER = "ROLE_USER";

    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;
    private AbstractMapper<UserDto, UserEntity> mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder, AbstractMapper<UserDto, UserEntity> mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(user -> {
            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.
                    User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, roles);
        }).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", email)));
    }

    @Override
    @Transactional
    public UserEntity createNewAccount(UserDto userDto) {
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

    private UserEntity mapDtoToUser(UserDto userDto) {
        UserEntity user = mapper.toEntity(userDto);
        user.setPassword(encryptPassword(user.getPassword()));
        user.getRoles().add(getUserRole());
        return user;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private RoleEntity getUserRole() {
        return roleService.findRoleByName(ROLE_USER);
    }
}
