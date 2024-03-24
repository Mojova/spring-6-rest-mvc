package fi.hovukas.spring6restmvc.repositories;

import fi.hovukas.spring6restmvc.bootstrap.BootstrapData;
import fi.hovukas.spring6restmvc.entities.Beer;
import fi.hovukas.spring6restmvc.model.BeerStyle;
import fi.hovukas.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("Sandels")
                .upc("123")
                .beerStyle(BeerStyle.LAGER)
                .price(new BigDecimal("1.99")).build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testGetBeerByName() {
        var list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(list.size()).isEqualTo(337);
    }
}