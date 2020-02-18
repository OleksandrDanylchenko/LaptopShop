package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.domain.CPU;
import ua.alexd.domain.HDD;

import java.util.List;

@Repository
@Transactional
public interface HDDRepo extends CrudRepository<HDD, Integer>, JpaSpecificationExecutor<HDD> {
    @Query(value = "SELECT hdd.model FROM HDD hdd")
    List<String> getAllModels();

    HDD findByModel(String model);
}