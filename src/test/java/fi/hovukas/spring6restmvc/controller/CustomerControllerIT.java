package fi.hovukas.spring6restmvc.controller;

import fi.hovukas.spring6restmvc.mappers.CustomerMapper;
import fi.hovukas.spring6restmvc.model.CustomerDTO;
import fi.hovukas.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testListCustomers() {
        var dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList() {
        customerRepository.deleteAll();
        var dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void getCustomerById() {
        var customer = customerRepository.findAll().get(0);

        var dto = customerController.getCustomerById(customer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testCustomerGetByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void saveAddCustomerTest() {
        var customerDto = CustomerDTO.builder().customerName("New Customer").build();

        ResponseEntity<String> responseEntity = customerController.addCustomer(customerDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        var savedCustomer = customerRepository.findById(savedUUID);
        assertThat(savedCustomer).isNotNull();
    }

    @Test
    void updateExistingCustomer() {
        var customer = customerRepository.findAll().get(0);
        var dto = customerMapper.customerToCustomerDto(customer);
        dto.setId(null);
        dto.setVersion(null);
        final String customerName = "UPDATED";
        dto.setCustomerName(customerName);

        ResponseEntity<String> responseEntity = customerController.updateCustomer(customer.getId(), dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var updatedBeer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedBeer.getCustomerName()).isEqualTo(customerName);
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {
        var customer = customerRepository.findAll().get(0);

        var responseEntity = customerController.deleteCustomer(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomer(UUID.randomUUID());
        });
    }
}