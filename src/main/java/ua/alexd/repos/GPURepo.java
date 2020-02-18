package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.domain.CPU;
import ua.alexd.domain.GPU;

import java.util.List;

@Repository
@Transactional
public interface GPURepo extends CrudRepository<GPU, Integer>, JpaSpecificationExecutor<GPU> {
    @Query(value = "SELECT gpu.model FROM GPU gpu")
    List<String> getAllModels();

    List<GPU> findByModel(String model);
}