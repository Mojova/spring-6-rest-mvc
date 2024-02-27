package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Optional<Customer> getCustomerById(UUID id);

    Customer addCustomer(Customer customer);

    void updateCustomer(UUID id, Customer customer);

    void deleteCustomer(UUID id);

    void patchCustomerById(UUID customerId, Customer customer);
}
