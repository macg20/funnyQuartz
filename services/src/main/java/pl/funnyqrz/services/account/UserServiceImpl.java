package pl.funnyqrz.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import pl.funnyqrz.enums.EventLogType;
import pl.funnyqrz.exceptions.ActivateAccountException;
import pl.funnyqrz.exceptions.UserAlreadyRegisterException;
import pl.funnyqrz.mappers.AbstractMapper;
import pl.funnyqrz.mappers.dto.UserDto;
import pl.funnyqrz.repositories.UserRepository;
import pl.funnyqrz.services.AbstractService;
import pl.funnyqrz.services.email.EmailService;
import pl.funnyqrz.services.eventlog.EventLogService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static pl.funnyqrz.messages.SystemMessage.USER_ALREADY_EXISTS;

@Service
public class UserServiceImpl extends AbstractService implements UserDetailsService, UserService {

    private static final String ROLE_USER = "ROLE_USER";

    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;
    private AbstractMapper<UserDto, UserEntity> mapper;
    private EmailService emailService;
    private String serverAddress;
    private EventLogService eventLogService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder,
                           AbstractMapper<UserDto, UserEntity> mapper, EmailService emailService,
                           @Value("${hostname}") String serverAddress,
                           EventLogService eventLogService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.emailService = emailService;
        this.serverAddress = serverAddress;
        this.eventLogService = eventLogService;
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
        UserEntity user = mapDtoToUser(userDto);
        sendActivationEmail(user.getEmail(), user.getActivateHash());
        return userRepository.save(user);
    }

    @Override
    public Set<String> findAllEmails() {
        return userRepository.findAllEmails();
    }

    @Override
    @Transactional
    public void activateAccount(String activateHash) {

        UserEntity userEntity = userRepository.findByActivateHash(activateHash);

        if (userEntity != null && !userEntity.isEnabled()) {
            userEntity.setEnabled(true);
            return;
        }

        throw new ActivateAccountException("User activate hash is not valid");
    }

    private void isExists(String email) {
        userRepository.ifExistsUserEmail(email).stream().findFirst().ifPresent(user -> {
            throw new UserAlreadyRegisterException(USER_ALREADY_EXISTS);
        });
    }

    private UserEntity mapDtoToUser(UserDto userDto) {
        UserEntity user = mapper.toEntity(userDto);
        user.setPassword(encryptPassword(user.getPassword()));
        user.setActivateHash(generateUserActivatedHash(user.getEmail()));
        user.getRoles().add(getUserRole());
        return user;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private RoleEntity getUserRole() {
        return roleService.findRoleByName(ROLE_USER);
    }

    private String generateUserActivatedHash(String emailAddress) {
        return Base64.getEncoder().encode(emailAddress.getBytes()) + UUID.randomUUID().toString();
    }

    private String generateActivateLink(String activatedHash) {
        return String.format("%s/auth/activate/%s", serverAddress, activatedHash);
    }

    private void sendActivationEmail(String emailAddress, String activationHash) {
        try {
            emailService.sendMessage("Activation Account", generateEmailContent(emailAddress, generateActivateLink(activationHash)),
                    Arrays.asList(emailAddress), Collections.EMPTY_LIST);
        } catch (Exception e) {
            getLogger().error("An Activation email for has not been sent.", e);
            eventLogService.registerEvent("An Activation email for has not been sent. -" + emailAddress,
                    LocalDateTime.now(), EventLogType.ERROR);
        }
    }

    private String generateEmailContent(String emailAddress, String activationLink) {
        return "Hi " + emailAddress + "\n" + "Your activation link: \n" + activationLink;
    }

}
