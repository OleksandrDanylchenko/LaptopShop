package ua.alexd.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.domain.Shop;

import java.util.List;

@Repository
@Transactional
public interface ShopRepo extends CrudRepository<Shop, Integer> {
    @Query(value = "SELECT s.address FROM Shop s")
    List<String> getAllAddresses();

    Shop findByAddress(String address);
}