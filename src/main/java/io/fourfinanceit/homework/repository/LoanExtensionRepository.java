package io.fourfinanceit.homework.repository;

import io.fourfinanceit.homework.entity.LoanExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "loan-extension")
public interface LoanExtensionRepository extends JpaRepository<LoanExtension, Long> {
    List<LoanExtension> findByLoanId(Long loanId);
}
