package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.domain.Display;

import java.util.List;

@Repository
@Transactional
public interface DisplayRepo extends CrudRepository<Display, Integer>, JpaSpecificationExecutor<Display> {
    @Query(value = "SELECT d.model FROM Display d")
    List<String> getAllModels();

    List<Display> findByModel(String model);
}