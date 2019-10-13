package org.alain.library.api.model.user;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;

    @ManyToMany( mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        StringBuilder listUsers = new StringBuilder();
        for (User u : this.users) {
            listUsers.append("\n\tUser id : ").append(u.getId()).append(", User email : ").append(u.getEmail());
        }

        return "Role{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", users :" + listUsers.toString() +
                '}';
    }
}
