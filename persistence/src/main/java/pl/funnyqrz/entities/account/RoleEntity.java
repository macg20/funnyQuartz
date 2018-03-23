package pl.funnyqrz.entities.account;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;

@Entity
@Table(name = "ROLES")
@GenericGenerator(name = "roles_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "sequence_name", value = "roles_sequence"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")})
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(generator = "roles_generator")
    private BigInteger id;

    @NotBlank
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

}
