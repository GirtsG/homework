package io.fourfinanceit.homework.value;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanApplicationResource extends ResourceSupport {
    @NonNull
    @Positive
    private BigDecimal amount;
    @NonNull
    @Positive
    private Integer termInDays;
    @NonNull
    @NotBlank
    private String ipAddress;
    @NonNull
    @NotBlank
    private String borrowerPersonId;
    @NonNull
    @NotBlank
    private String borrowerFullName;
}
