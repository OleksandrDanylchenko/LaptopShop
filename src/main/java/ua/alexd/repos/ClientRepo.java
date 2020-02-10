package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Client;

public interface ClientRepo extends CrudRepository<Client, Long> {
}