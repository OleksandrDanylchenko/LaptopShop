package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Producer;

public interface ProducerRepo extends CrudRepository<Producer, Long> {
}