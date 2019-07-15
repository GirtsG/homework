package io.fourfinanceit.homework.repository;

import io.fourfinanceit.homework.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByPersonId_thenReturnOnePerson() {
        final String personId = "010190-10028";
        final String personFullName = "Juris Ozols";
        Customer customer = new Customer();
        customer.setPersonId(personId);
        customer.setFullName(personFullName);

        entityManager.persist(customer);
        entityManager.flush();

        Optional<Customer> result = customerRepository.findByPersonId(personId);
        assertTrue(result.isPresent());
        assertEquals(personId, result.get().getPersonId());
        assertEquals(personFullName, result.get().getFullName());
        assertNotNull(result.get().getId());
    }
}
