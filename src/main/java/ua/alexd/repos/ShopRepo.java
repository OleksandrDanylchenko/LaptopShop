package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Shop;

public interface ShopRepo extends CrudRepository<Shop, Long> {
}