package fi.hovukas.spring6restmvc.bootstrap;

import fi.hovukas.spring6restmvc.repositories.BeerRepository;
import fi.hovukas.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootsrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootsrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootsrapData(beerRepository, customerRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run("");

        assertThat(beerRepository.count()).isEqualTo(2);
        assertThat(customerRepository.count()).isEqualTo(2);

    }
}