package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listBeers();

    BeerDTO saveNewBeer(BeerDTO beer);

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
