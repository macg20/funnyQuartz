package pl.funnyqrz.tests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import pl.funnyqrz.mappers.dto.UserDto;
import pl.funnyqrz.repositories.UserRepository;
import pl.funnyqrz.services.account.UserServiceImpl;
import pl.funnyqrz.tests.AbstractTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.funnyqrz.tests.constants.UserRegisterConstants.*;

public class UserDetailsServiceTest extends AbstractTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;



    @Test
    public void findUserTest() {

        //given
        registerUser();

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername(SAMPLE_EMAIL);

        // then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(SAMPLE_EMAIL);
        assertThat(userDetails.getPassword()).isNotNull();
        assertThat(userDetails.getAuthorities().size()).isNotZero();
    }

    @Test
    public void userNotFound() {

        //when
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(DUMMY_EMAIL));

        //then
        assertThat(exception).isNotNull();
        assertEquals(String.format("User %s not found", DUMMY_EMAIL), exception.getMessage());
    }

    private void registerUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail(SAMPLE_EMAIL);
        userDto.setFirstName(SAMPLE_FIRST_NAME);
        userDto.setLastName(SAMPLE_LAST_NAME);
        userDto.setPassword(SAMPLE_PASSWORD);
        userDto.setEnabled(true);
        userService.createNewAccount(userDto);
    }

}
