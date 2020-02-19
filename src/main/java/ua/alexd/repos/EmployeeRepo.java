package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.alexd.domain.Employee;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepo extends CrudRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    @Query(value = "SELECT e.id FROM Employee e")
    List<Integer> getAllIds();
}