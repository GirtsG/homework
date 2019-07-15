package io.fourfinanceit.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoanExtension extends AbstractEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Loan loan;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private LoanApplication loanApplication;
    private Integer termInDays;
}
