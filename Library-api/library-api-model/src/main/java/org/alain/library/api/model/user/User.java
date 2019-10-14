package org.alain.library.api.model.user;

import lombok.*;
import org.alain.library.api.model.loan.Loan;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "LibraryUser")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;

    @NotNull
    @Size( min = 2, max=30)
    @Column (length = 30)
    private String firstName;
    @NotNull
    @Size( min = 2, max=30)
    @Column (length = 30)
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable( name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles.add(role);
    }

    public void addRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return 56;
    }
}
