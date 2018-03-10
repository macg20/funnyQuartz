package pl.funnyqrz.entities.account;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "USERS")
@GenericGenerator(name = "users_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "users_sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")})
@Data
public class UserEntity {

    @Id
    @GeneratedValue(generator = "users_generator")
    private BigInteger id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles = new HashSet<>();


}
