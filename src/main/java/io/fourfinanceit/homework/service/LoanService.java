package io.fourfinanceit.homework.service;

import io.fourfinanceit.homework.entity.Customer;
import io.fourfinanceit.homework.entity.Loan;
import io.fourfinanceit.homework.entity.LoanApplication;
import io.fourfinanceit.homework.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final ModelMapper mapper;
    private final LoanRepository repository;
    private final LoanCalculationService loanCalculationService;

    @Transactional
    Loan createNewLoanFromApplication(LoanApplication loanApplication, Customer customer) {
        Loan loan = new Loan();
        mapper.map(loanApplication, loan);
        loan.setBorrower(customer);
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusDays(loanApplication.getTermInDays()));
        loan.setLoanApplication(loanApplication);
        return loanCalculationService.performLoanReCalculation(repository.save(loan).getId());
    }
}
