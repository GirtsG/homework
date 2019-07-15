package io.fourfinanceit.homework.service;

import io.fourfinanceit.homework.entity.Loan;
import io.fourfinanceit.homework.entity.LoanExtension;
import io.fourfinanceit.homework.repository.LoanExtensionRepository;
import io.fourfinanceit.homework.repository.LoanRepository;
import io.fourfinanceit.homework.value.LoanExtensionResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanExtensionService {

    private final LoanRepository loanRepository;
    private final LoanExtensionRepository repository;
    private final LoanCalculationService loanCalculationService;

    public Optional<Loan> findLoanById(Long loanId) {
        return this.loanRepository.findById(loanId);
    }

    @Transactional
    public LoanExtension extendLoan(@Validated LoanExtensionResource resource) {
        LoanExtension loanExtension = new LoanExtension();
        loanExtension.setTermInDays(resource.getTermInDays());
        Loan loan = loanRepository.getOne(resource.getLoanId());
        loanExtension.setLoan(loan);
        loanExtension.setLoanApplication(loan.getLoanApplication());
        repository.save(loanExtension);
        loanCalculationService.performLoanReCalculation(loan.getId());
        return repository.getOne(loanExtension.getId());
    }
}
