package pl.funnyqrz.entities.userAccount;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@GenericGenerator(name = "user_sequence",strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "user_sequence"),
                @Parameter(name ="initial_value", value = "1"),
                @Parameter(name = "increment_value", value = "1")
        })
public class User {

        @Id
        @GeneratedValue(generator = "user_sequence")
        private Long id;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private Boolean active;
        private Boolean tokenExpired;

        @ManyToMany
                @JoinTable(
                name ="users_roles",
                        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
                )
        private Collection<Role> roles;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getEmailAddress() {
                return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
                this.emailAddress = emailAddress;
        }

        public Boolean getActive() {
                return active;
        }

        public void setActive(Boolean active) {
                this.active = active;
        }

        public Boolean getTokenExpired() {
                return tokenExpired;
        }

        public void setTokenExpired(Boolean tokenExpired) {
                this.tokenExpired = tokenExpired;
        }

        public Collection<Role> getRoles() {
                return roles;
        }

        public void setRoles(Collection<Role> roles) {
                this.roles = roles;
        }
}
