package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Label;
import ua.alexd.domain.Laptop;

import java.util.List;

public interface LaptopRepo extends CrudRepository<Laptop, Integer>, JpaSpecificationExecutor<Laptop> {
    @Query(value = "SELECT l.label.model FROM Laptop l")
    List<String> getAllModels();

    Laptop findByLabelModel(String model);
}