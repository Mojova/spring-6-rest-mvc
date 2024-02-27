package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO addCustomer(CustomerDTO customer);

    void updateCustomer(UUID id, CustomerDTO customer);

    void deleteCustomer(UUID id);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
