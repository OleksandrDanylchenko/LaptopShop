package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Basket;

public interface BasketRepo extends CrudRepository<Basket, Long> {
}