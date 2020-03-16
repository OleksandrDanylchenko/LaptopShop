package ua.alexd.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Employee;

import java.util.List;

public interface BasketRepo extends CrudRepository<Basket, Integer>, JpaSpecificationExecutor<Basket> {
    @Query(value = "SELECT b.id FROM Basket b")
    List<Integer> getAllIds();

    @Query(value = "SELECT b.employee FROM Basket b")
    List<Employee> getEmployees();
}