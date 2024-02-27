package fi.hovukas.spring6restmvc.mappers;

import fi.hovukas.spring6restmvc.entities.Customer;
import fi.hovukas.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDto(Customer customer);
}
