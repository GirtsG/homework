package io.fourfinanceit.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customer extends AbstractEntity {

    @NonNull
    private String personId;
    @NonNull
    private String fullName;
    @OneToMany(mappedBy = "borrower")
    private Collection<Loan> loans;
}
