package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customerMap;


    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        var customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("McGill, Charles")
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
        var customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("White, Walter")
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
        var customer3 = Customer.builder()
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
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }
}
