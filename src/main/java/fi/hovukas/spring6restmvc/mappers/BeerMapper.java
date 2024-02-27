package fi.hovukas.spring6restmvc.mappers;

import fi.hovukas.spring6restmvc.entities.Beer;
import fi.hovukas.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
