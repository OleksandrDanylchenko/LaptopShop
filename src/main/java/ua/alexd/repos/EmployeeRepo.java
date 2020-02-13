package ua.alexd.repos;

import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Employee;

import java.util.List;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {
    List<Employee> findByFirstName(String firstName);

    List<Employee> findBySecondName(String secondName);

    List<Employee> findByFirstNameAndSecondName(String firstName, String secondName);
}