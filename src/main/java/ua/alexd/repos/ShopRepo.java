package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Shop;

import java.util.List;

public interface ShopRepo extends CrudRepository<Shop, Integer> {
    List<Shop> findByAddress(String address);
}