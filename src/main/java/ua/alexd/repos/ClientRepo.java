package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.domain.Client;

@Repository
@Transactional
public interface ClientRepo extends CrudRepository<Client, Integer>, JpaSpecificationExecutor<Client> { }