package io.fourfinanceit.homework.service;

import io.fourfinanceit.homework.entity.Customer;
import io.fourfinanceit.homework.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer findOrCreateCustomer(Customer customer) {
        return customerRepository.findByPersonId(customer.getPersonId())
                .orElseGet(() -> customerRepository.save(customer));
    }
}
