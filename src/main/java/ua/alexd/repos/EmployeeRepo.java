package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {
}