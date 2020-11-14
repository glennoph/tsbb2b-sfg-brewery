package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        System.out.println("BeerOrderControllerIT");
        customer = customerRepository.findAll().get(0);
        System.out.println(customer.getId());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test() {
        System.out.println("wip");
    }
}
