package pl.funnyqrz.mapper.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.math.BigInteger;

@Data
public class UserDto {

    private BigInteger id;

    @NotBlank
    @Size(min = 3, message = "First Name should be min 3 chars")
    private String firstName;

    @NotBlank
    @Size(min = 3, message = "Last name should be min 3 chars")
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, message = "Password should be min 3 chars")
    private String password;

    private boolean enabled;

}
