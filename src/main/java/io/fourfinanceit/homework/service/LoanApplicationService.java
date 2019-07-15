package io.fourfinanceit.homework.service;

import io.fourfinanceit.homework.entity.Customer;
import io.fourfinanceit.homework.entity.LoanApplication;
import io.fourfinanceit.homework.repository.LoanApplicationRepository;
import io.fourfinanceit.homework.value.LoanApplicationResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final ModelMapper mapper;
    private final LoanApplicationRepository repository;
    private final CustomerService customerService;
    private final LoanService loanService;
    private final RiskService riskService;

    @Transactional
    public LoanApplication createLoanApplication(@Validated LoanApplicationResource resource) {
        LoanApplication loanApplication = new LoanApplication();
        mapper.map(resource, loanApplication);
        loanApplication.setApplyDate(LocalDate.now());
        loanApplication = repository.save(loanApplication);

        Customer customer = new Customer();
        customer.setPersonId(resource.getBorrowerPersonId());
        customer.setFullName(resource.getBorrowerFullName());

        loanApplication.setLoan(loanService.createNewLoanFromApplication(loanApplication,
                customerService.findOrCreateCustomer(customer)));
        return riskService.performRiskAnalysis(repository.save(loanApplication));
    }
}
