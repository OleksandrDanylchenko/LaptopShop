package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Status;

public interface StatusRepo extends CrudRepository<Status, Long> {
}