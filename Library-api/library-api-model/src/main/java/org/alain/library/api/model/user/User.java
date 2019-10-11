package org.alain.library.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alain.library.api.model.loan.Loan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();

    @ManyToMany
    @JoinTable (name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_designation"))
    private List<Role> roles = new ArrayList<>();

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.roles.add(role);
    }

    public void addRole(Role role){
        this.roles.add(role);
    }
}
