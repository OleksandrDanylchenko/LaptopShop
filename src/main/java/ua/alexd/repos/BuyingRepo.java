package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Buying;

public interface BuyingRepo extends CrudRepository<Buying, Integer>, JpaSpecificationExecutor<Buying> { }