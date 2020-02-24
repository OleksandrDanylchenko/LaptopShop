package ua.alexd.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.domain.Type;

import java.util.List;

@Repository
@Transactional
public interface TypeRepo extends CrudRepository<Type, Integer> {
    @Query(value = "SELECT t.name FROM Type t")
    List<String> getAllNames();

    List<Type> findByName(String name);
}