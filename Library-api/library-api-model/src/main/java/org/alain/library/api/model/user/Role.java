package org.alain.library.api.model.user;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(this.getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return 56;
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
