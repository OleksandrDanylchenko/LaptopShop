package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Type;

import java.util.List;

public interface TypeRepo extends CrudRepository<Type, Integer> {
    List<Type> findByName(String name);
}