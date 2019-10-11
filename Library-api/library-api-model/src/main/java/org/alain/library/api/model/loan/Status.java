package org.alain.library.api.model.loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @Enumerated(EnumType.STRING)
    private StatusDesignation statusDesignation;

    @OneToMany(mappedBy = "status", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LoanStatus> loanStatuses = new ArrayList<>();

}
