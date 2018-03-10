package pl.funnyqrz.mappers;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.funnyqrz.entities.account.RoleEntity;
import pl.funnyqrz.entities.account.UserEntity;
import pl.funnyqrz.mappers.dto.UserDto;

import java.util.Set;

@Component("userMapper")
public class UserMapper implements AbstractMapper<UserDto, UserEntity> {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEnabled(entity.isEnabled());
        return dto;
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setEnabled(true);
        Set<RoleEntity> userRole = Sets.newHashSet();
        user.setRoles(userRole);
        return user;
    }
}
