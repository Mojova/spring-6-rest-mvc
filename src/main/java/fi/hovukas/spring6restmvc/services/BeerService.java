package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);

    List<Beer> listBeers();
}
