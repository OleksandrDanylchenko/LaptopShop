package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.GPU;

public interface GPURepo extends CrudRepository<GPU, Long> {
}