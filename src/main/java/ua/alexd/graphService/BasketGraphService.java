package ua.alexd.graphService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.alexd.repos.BasketRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Lazy
public class BasketGraphService {
    private BasketRepo basketRepo;

    public BasketGraphService(BasketRepo basketRepo) {
        this.basketRepo = basketRepo;
    }

    public List<Map<Object,Object>> getEmployeesDataPoints() {
        var employeesDataPoints = new ArrayList<Map<Object, Object>>();

        var employeesSells = getEmployeesAndSells();
        employeesSells.forEach((employeeName, sellsNum) -> {
            var recordMap = new HashMap<>();
            recordMap.put("label", employeeName);
            recordMap.put("y", sellsNum);
            employeesDataPoints.add(recordMap);
        });
        return employeesDataPoints;
    }

    @NotNull
    private Map<String, Integer> getEmployeesAndSells() {
        var employeeSells = new HashMap<String, Integer>();

        var employeeBasketRecords = basketRepo.getEmployees();
        for (var employee : employeeBasketRecords) {
            var employeeFullName = employee.getFirstName() + ' ' + employee.getSecondName();
            employeeSells.putIfAbsent(employeeFullName, 0);
            //noinspection ConstantConditions
            employeeSells.compute(employeeFullName, (key, value) -> ++value);
        }
        return employeeSells;
    }
}