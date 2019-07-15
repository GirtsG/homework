package io.fourfinanceit.homework.repository;

import io.fourfinanceit.homework.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "customer")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPersonId(String personId);
}
