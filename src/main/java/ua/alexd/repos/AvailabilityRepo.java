package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Availability;

public interface AvailabilityRepo extends CrudRepository<Availability, Long> {
}