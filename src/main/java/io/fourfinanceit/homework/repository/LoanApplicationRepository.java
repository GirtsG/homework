package io.fourfinanceit.homework.repository;

import io.fourfinanceit.homework.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(path = "loan-application")
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByIpAddressAndApplyDate(String ipAddress, LocalDate startDate);
}
