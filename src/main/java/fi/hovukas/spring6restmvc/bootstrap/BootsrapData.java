package fi.hovukas.spring6restmvc.bootstrap;

import fi.hovukas.spring6restmvc.entities.Beer;
import fi.hovukas.spring6restmvc.entities.Customer;
import fi.hovukas.spring6restmvc.model.BeerStyle;
import fi.hovukas.spring6restmvc.repositories.BeerRepository;
import fi.hovukas.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class BootsrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Beer beer1 = Beer.builder()
                .beerName("Sandels")
                .beerStyle(BeerStyle.LAGER)
                .price(BigDecimal.valueOf(2.99))
                .upc("1231243")
                .quantityOnHand(4)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Beer beer2 = Beer.builder()
                .beerName("Mielikuvitus-IPA")
                .beerStyle(BeerStyle.IPA)
                .price(BigDecimal.valueOf(3.99))
                .upc("42534")
                .quantityOnHand(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);

        Customer customer1 = Customer.builder()
                .customerName("Ankka, Aku")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .customerName("Hanhi, Hannu")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }
}
