package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO addCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customer);

    Boolean deleteCustomer(UUID id);

    Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer);
}
