package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Type;

public interface TypeRepo extends CrudRepository<Type, Long> {
}