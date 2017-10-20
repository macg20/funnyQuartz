package pl.funnyqrz;


import org.junit.Test;
import pl.funnyqrz.wrappers.EmailAddress;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class EmailWrapperTest {

    @Test
    public void emailWrapperConvertToStringArrayTest() {
        //given
        Set<EmailAddress> addresses = prepareData();

        //when
        String[] adressesArray = addresses.stream().map(x -> x.getValue()).toArray(String[]::new);

        //then
        assertThat(adressesArray.length).isEqualTo(addresses.size());
        assertThat(Arrays.asList(adressesArray).contains("1@gmail.com")).isTrue();
        assertThat(Arrays.asList(adressesArray).contains("2@gmail.com")).isTrue();
        assertThat(Arrays.asList(adressesArray).contains("3@gmail.com")).isTrue();


    }

    private Set<EmailAddress> prepareData() {

        Set<EmailAddress> addresses = new HashSet<>();
        addresses.add(new EmailAddress("1@gmail.com"));
        addresses.add(new EmailAddress("2@gmail.com"));
        addresses.add(new EmailAddress("3@gmail.com"));

        return addresses;
    }
}