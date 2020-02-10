package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Laptop;

public interface LaptopRepo extends CrudRepository<Laptop, Long> {
}