package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.RAM;

public interface RAMRepo extends CrudRepository<RAM, Long> {
}