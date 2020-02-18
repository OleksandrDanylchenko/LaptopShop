package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.HDD;

public interface HDDRepo extends CrudRepository<HDD, Integer>, JpaSpecificationExecutor<HDD> { }