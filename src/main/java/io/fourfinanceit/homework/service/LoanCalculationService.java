package io.fourfinanceit.homework.service;

import io.fourfinanceit.homework.entity.Loan;
import io.fourfinanceit.homework.entity.LoanExtension;
import io.fourfinanceit.homework.repository.LoanExtensionRepository;
import io.fourfinanceit.homework.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanCalculationService {
    private final LoanRepository loanRepository;
    private final LoanExtensionRepository loanExtensionRepository;
    @Value("${fourfinance.homework.calculation.interest-factor-per-week}")
    private BigDecimal interestFactorPerWeek;
    @Value("${fourfinance.homework.calculation.days-in-financial-year}")
    private BigDecimal daysInFinancialYear;
    @Value("${fourfinance.homework.calculation.default-interest-rate-per-year-percents}")
    private BigDecimal defaultInterestRatePerYearPercents;

    @Transactional
    Loan performLoanReCalculation(Long loanId) {
        Loan loan = loanRepository.getOne(loanId);
        if (loan.getInterestRate() == null) {
            loan.setInterestRate(defaultInterestRatePerYearPercents.setScale(2, RoundingMode.HALF_UP));
        }
        List<LoanExtension> loanExtensions = loanExtensionRepository.findByLoanId(loan.getId());
        loan.setEndDate(calculateEndDate(loan, loanExtensions));
        loan.setTotalRepayAmount(calculateTotalRepayAmount(loan, loanExtensions).setScale(2, RoundingMode.HALF_UP));
        return loanRepository.save(loan);
    }

    private BigDecimal calculateTotalRepayAmount(Loan loan, List<LoanExtension> loanExtensions) {
        BigDecimal totalRepayAmount = loan.getAmount();
        BigDecimal interestFactor = getInterestFactor(loan.getInterestRate());
        BigDecimal extensionInterestFactor = getExtensionInterestFactor(interestFactor);
        totalRepayAmount = addTotalRepayAmount(totalRepayAmount, interestFactor, loan.getTermInDays());
        if (!CollectionUtils.isEmpty(loanExtensions)) {
            for (LoanExtension loanExtension : loanExtensions) {
                totalRepayAmount = addTotalRepayAmount(totalRepayAmount, extensionInterestFactor, loanExtension.getTermInDays());
            }
        }
        return totalRepayAmount;
    }

    private BigDecimal addTotalRepayAmount(BigDecimal totalRepayAmount, BigDecimal interestFactor, Integer days) {
        BigDecimal interestAmount = totalRepayAmount.multiply(interestFactor).multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_UP);
        return totalRepayAmount.add(interestAmount).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getInterestFactor(BigDecimal interestRate) {
        BigDecimal yearlyRateFactor = interestRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return yearlyRateFactor.divide(daysInFinancialYear, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getExtensionInterestFactor(BigDecimal interestFactor) {
        BigDecimal daysInWeek = BigDecimal.valueOf(DayOfWeek.values().length);
        return interestFactor.multiply(daysInWeek)
                .multiply(interestFactorPerWeek).divide(daysInWeek, 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
    }

    private LocalDate calculateEndDate(Loan loan, List<LoanExtension> loanExtensions) {
        LocalDate startDate = loan.getStartDate();
        Integer days = loan.getLoanApplication().getTermInDays();
        if (!CollectionUtils.isEmpty(loanExtensions)) {
            for (LoanExtension loanExtension : loanExtensions) {
                days = days + loanExtension.getTermInDays();
            }
        }
        return startDate.plusDays(days);
    }
}
