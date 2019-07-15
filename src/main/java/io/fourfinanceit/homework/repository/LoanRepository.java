package io.fourfinanceit.homework.repository;

import io.fourfinanceit.homework.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "loan")
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
