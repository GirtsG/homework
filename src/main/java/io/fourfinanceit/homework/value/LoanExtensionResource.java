package io.fourfinanceit.homework.value;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanExtensionResource extends ResourceSupport {

    @NonNull
    @Positive
    private Integer termInDays;

    @NonNull
    @Positive
    private Long loanId;
}
