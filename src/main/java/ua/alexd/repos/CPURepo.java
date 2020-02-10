package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.CPU;

public interface CPURepo extends CrudRepository<CPU, Long> {
}