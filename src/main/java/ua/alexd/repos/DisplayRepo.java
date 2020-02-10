package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Display;

public interface DisplayRepo extends CrudRepository<Display, Long> {
}