package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Hardware;

import java.util.List;

public interface HardwareRepo extends CrudRepository<Hardware, Integer>, JpaSpecificationExecutor<Hardware> {
    @Query(value = "SELECT h.assemblyName FROM Hardware h")
    List<String> getAllAssemblyNames();

    Hardware findByAssemblyName(String assemblyName);
}