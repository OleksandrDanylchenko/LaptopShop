package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Basket;

public interface BasketRepo extends CrudRepository<Basket, Integer>, JpaSpecificationExecutor<Basket> { }