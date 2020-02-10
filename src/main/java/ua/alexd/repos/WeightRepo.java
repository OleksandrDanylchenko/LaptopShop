package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Weight;

public interface WeightRepo extends CrudRepository<Weight, Long> {
}