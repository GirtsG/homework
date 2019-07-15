package io.fourfinanceit.homework.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fourfinanceit.homework.converter.LocalDateDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Loan extends AbstractEntity {
    @NotNull
    @NonNull
    private BigDecimal amount;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    private LocalDate startDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    private LocalDate endDate;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer borrower;
    private Integer termInDays;
    private BigDecimal interestRate;
    private BigDecimal totalRepayAmount;
    @OneToOne
    private LoanApplication loanApplication;
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private List<LoanExtension> loanExtensions;
}
