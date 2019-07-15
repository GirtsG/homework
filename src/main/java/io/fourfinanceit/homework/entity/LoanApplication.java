package io.fourfinanceit.homework.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fourfinanceit.homework.converter.LocalDateDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoanApplication extends AbstractEntity {

    @Positive
    private BigDecimal amount;
    @NotBlank
    private String ipAddress;
    @Positive
    private Integer termInDays;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    private LocalDate applyDate;
    @Enumerated(EnumType.STRING)
    private LoanApplicationRiskStatus riskStatus;
    @OneToOne(fetch = FetchType.EAGER)
    private Loan loan;
}
