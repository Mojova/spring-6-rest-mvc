package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, CustomerDTO> customerMap;


    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        var customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("McGill, Charles")
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
        var customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("White, Walter")
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
        var customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("Schrader, Hank")
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customer) {
        var savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName(customer.getCustomerName())
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customer) {
        var savedCustomer = customerMap.get(id);

        savedCustomer.setCustomerName(customer.getCustomerName());
        savedCustomer.setUpdateDate(LocalDateTime.now());
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerMap.remove(id);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {
        var existingCustomer = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }
        existingCustomer.setUpdateDate(LocalDateTime.now());
    }
}
