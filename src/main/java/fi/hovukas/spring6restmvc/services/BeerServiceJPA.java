package fi.hovukas.spring6restmvc.services;

import fi.hovukas.spring6restmvc.entities.Beer;
import fi.hovukas.spring6restmvc.mappers.BeerMapper;
import fi.hovukas.spring6restmvc.model.BeerDTO;
import fi.hovukas.spring6restmvc.model.BeerStyle;
import fi.hovukas.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    private static final  int DEFAULT_PAGE = 0;
    private static final int PAGE_SIZE = 25;

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = DEFAULT_PAGE;
        int queryPageSize = PAGE_SIZE;

        if (pageNumber != null && pageNumber >= 0) {
            queryPageNumber = pageNumber - 1;
        }
        if (pageSize != null && pageSize >= 1000) {
            queryPageSize = 1000;
        } else if (pageSize != null && pageSize >= 0) {
            queryPageSize = pageSize;
        }

        Sort sort = Sort.by(Sort.Order.asc("beerName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        var pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName)) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else if (beerStyle != null) {
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    public Page<Beer> listBeersByName(String beerName, Pageable pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageRequest);
    }

    public Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageRequest) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
    }

    public Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageRequest);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(existingBeer -> {
            if (StringUtils.hasText(beer.getBeerName())) {
                existingBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null) {
                existingBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (beer.getPrice() != null) {
                existingBeer.setPrice(beer.getPrice());
            }
            if (StringUtils.hasText(beer.getUpc())) {
                existingBeer.setUpc(beer.getUpc());
            }
            if (beer.getQuantityOnHand() != null) {
                existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(existingBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
