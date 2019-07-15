package io.fourfinanceit.homework.service;

import io.fourfinanceit.homework.entity.LoanApplication;
import io.fourfinanceit.homework.entity.LoanApplicationRiskStatus;
import io.fourfinanceit.homework.repository.LoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskService {
    private final LoanApplicationRepository repository;
    @Value("${fourfinance.homework.risk.start-time}")
    private LocalTime riskStartTime;
    @Value("${fourfinance.homework.risk.end-time}")
    private LocalTime riskEndTime;
    @Value("${fourfinance.homework.risk.max-applications-per-day}")
    private Integer maxApplicationsPerDay;
    @Value("${fourfinance.homework.risk.max-loan-amount}")
    private BigDecimal maxLoanAmount;

    @Transactional
    public LoanApplication performRiskAnalysis(LoanApplication loanApplication) {
        if (isRiskValidationNeeded(loanApplication)) {
            loanApplication.setRiskStatus(LoanApplicationRiskStatus.PENDING);
            return this.sendToRiskValidationService(repository.save(loanApplication));
        } else {
            loanApplication.setRiskStatus(LoanApplicationRiskStatus.SAFE);
            return repository.save(loanApplication);
        }
    }

    private boolean isRiskValidationNeeded(LoanApplication loanApplication) {
        return isRiskyTimeAndAmount(loanApplication) || isMaxApplicationsPerDayReached(loanApplication);
    }

    private boolean isMaxApplicationsPerDayReached(LoanApplication loanApplication) {
        List<LoanApplication> applications = repository.findByIpAddressAndApplyDate(loanApplication.getIpAddress(), loanApplication.getCreatedTime().toLocalDate());
        return applications != null && applications.size() >= maxApplicationsPerDay;
    }

    private LoanApplication sendToRiskValidationService(LoanApplication loanApplication) {
        // Send LoanApplication data to appropriate risk validation service e.g. some JMS application.
        return loanApplication;
    }

    private boolean isRiskyTimeAndAmount(LoanApplication loanApplication) {
        return loanApplication.getLoan().getAmount().compareTo(maxLoanAmount) >= 0
                && loanApplication.getCreatedTime().toLocalTime().isAfter(riskStartTime)
                && loanApplication.getCreatedTime().toLocalTime().isBefore(riskEndTime);
    }
}
