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

    @NotNull
    public List<Map<Object, Object>> getEmployeesDataPoints() {
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
    public List<Map<Object, Object>> getClientsDataPoints() {
        var clientsDataPoints = new ArrayList<Map<Object, Object>>();

        var clientsBuyings = getClientsAndBuyings();
        clientsBuyings.forEach((clientName, buyNum) -> {
            var recordMap = new HashMap<>();
            recordMap.put("label", clientName);
            recordMap.put("y", buyNum);
            clientsDataPoints.add(recordMap);
        });
        return clientsDataPoints;
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

    @NotNull
    private Map<String, Integer> getClientsAndBuyings() {
        var clientBuyings = new HashMap<String, Integer>();

        var clientBasketRecords = basketRepo.getClients();
        for (var client : clientBasketRecords) {
            var clientFullName = client.getFirstName() + ' ' + client.getSecondName();
            clientBuyings.putIfAbsent(clientFullName, 0);
            //noinspection ConstantConditions
            clientBuyings.compute(clientFullName, (key, value) -> ++value);
        }
        return clientBuyings;
    }
}