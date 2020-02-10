package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Buying;

public interface BuyingRepo extends CrudRepository<Buying, Long> {
}