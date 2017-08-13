import org.junit.Test;
import pl.funnyqrz.utils.resource.PropertiesValidator;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesTest {


    @Test
    public void isEmptyTest() {

        //given
        String nullString = prepareNullString();
        String emptyString = prepareEmptyString();

        //when
        boolean nullStringValidationResult = PropertiesValidator.isEmpty(nullString);
        boolean emptyStringValidationResult = PropertiesValidator.isEmpty(emptyString);

        //then
        assertThat(nullStringValidationResult).isEqualTo(Boolean.TRUE);
        assertThat(emptyStringValidationResult).isEqualTo(Boolean.TRUE);
    }

    @Test
    public void isValidURL() {

        //given
        String validURL = prepareValidURL();
        String invalidURL = prepareInvalidURL();

        //when
        boolean validURLValidationResult = PropertiesValidator.isValidUrl(validURL);
        boolean invalidURLValidationResult = PropertiesValidator.isValidUrl(invalidURL);

        //then
        assertThat(validURLValidationResult).isEqualTo(Boolean.TRUE);
        assertThat(invalidURLValidationResult).isEqualTo(Boolean.FALSE);
    }

    private String prepareValidURL() {
        return "https://www.google.pl/";
    }

    private String prepareInvalidURL() {
        return "not_valid_url";
    }

    private String prepareNullString() {
        return null;
    }

    private String prepareEmptyString() {
        return "";
    }
}
