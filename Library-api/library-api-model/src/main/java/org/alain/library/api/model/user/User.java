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
@Table(name = "LibraryUser")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Size( min = 2, max=30)
    @Column (length = 30)
    private String firstName;

    @NotNull
    @Size( min = 2, max=30)
    @Column (length = 30)
    private String lastName;

    private int active;

    private String roles = "";

    private String permissions = "";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    public User(@NotNull @Email String email, @NotNull String password, @NotNull @Size(min = 2, max = 30) String firstName, @NotNull @Size(min = 2, max = 30) String lastName, String roles, String permissions) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public void addLoan(Loan loan){
        this.loans.add(loan);
        loan.setUser(this);
    }

    public void removeLoan(Loan loan){
        this.loans.remove(loan);
        loan.setUser(null);
    }

    public List<String> getRoleList(){
        if (this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if (this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

}
