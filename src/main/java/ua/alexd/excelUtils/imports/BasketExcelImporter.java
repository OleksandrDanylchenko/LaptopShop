package ua.alexd.excelUtils.imports;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Client;
import ua.alexd.domain.Employee;
import ua.alexd.repos.ClientRepo;
import ua.alexd.repos.EmployeeRepo;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

@Service
public class BasketExcelImporter {
    private EmployeeRepo employeeRepo;
    private ClientRepo clientRepo;

    public BasketExcelImporter(EmployeeRepo employeeRepo, ClientRepo clientRepo) {
        this.employeeRepo = employeeRepo;
        this.clientRepo = clientRepo;
    }

    @NotNull
    public List<Basket> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var basketSheet = workbook.getSheetAt(0);

        var basketTableFields = new String[]{"Id", "Id продавця", "Ім'я продавця", "Прізвище продавця",
                "Адреса магазину", "Id покупця", "Ім'я покупця", "Прізвище покупця", "Час покупки"};
        if (isValidTableStructure(basketSheet, basketTableFields)) {
            var dataFormatter = new DataFormatter();
            var newBaskets = new ArrayList<Basket>();

            var employeeColNum = 1;
            var clientColNum = 5;
            var dateTimeColNum = 8;

            for (Row row : basketSheet) {
                if (row.getRowNum() != 0) {
                    Employee employee = null;
                    Client client = null;
                    LocalDateTime dateTime = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (NumberUtils.isParsable(cellValue)) {
                            if (cell.getColumnIndex() == employeeColNum) {
                                var employeeId = Integer.parseInt(cellValue);
                                if (employeeRepo.findById(employeeId).isPresent())
                                    employee = employeeRepo.findById(employeeId).get();
                            } else if (cell.getColumnIndex() == clientColNum) {
                                var clientId = Integer.parseInt(cellValue);
                                if (clientRepo.findById(clientId).isPresent())
                                    client = clientRepo.findById(clientId).get();
                            }
                        } else if (cell.getColumnIndex() == dateTimeColNum)
                            try {
                                var dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");
                                dateTime = LocalDateTime.parse(cellValue, dateTimeFormat);
                            } catch (DateTimeException | ArrayIndexOutOfBoundsException ignored) {
                            }
                    }
                    addNewBasket(employee, client, dateTime, newBaskets);
                }
            }
            workbook.close();
            return newBaskets;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewBasket(Employee employee, Client client, LocalDateTime dateTime,
                                     ArrayList<Basket> newBaskets) {
        if (employee != null && client != null && dateTime != null) {
            var newBasket = new Basket(dateTime, employee, client);
            newBaskets.add(newBasket);
        }
    }
}