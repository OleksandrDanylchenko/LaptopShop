package ua.alexd.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Shop;

import java.util.List;

public interface ShopRepo extends CrudRepository<Shop, Integer> {
    @Query(value = "SELECT address FROM shops", nativeQuery = true)
    List<String> getAllAddresses();

    List<Shop> findByAddress(String address);
}