package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Employee;
import ua.alexd.domain.Shop;
import ua.alexd.repos.ShopRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class EmployeeExcelImporter {
    @NotNull
    public static List<Employee> importFile(String uploadedFilePath, ShopRepo shopRepo)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var employeeSheet = workbook.getSheetAt(0);

        var employeeTableFields = new String[]{"Id", "Ім'я", "Прізвище", "№ магазину", "Адреса магазину", "Статус"};
        if (isValidTableStructure(employeeSheet, employeeTableFields)) {
            var dataFormatter = new DataFormatter();
            var newEmployees = new ArrayList<Employee>();

            var firstName = "";
            var firstNameColNum = 1;
            var secondName = "";
            var secondNameColNum = 2;
            Shop shop = null;
            var shopColNum = 4;
            var isActive = false;
            var isActiveColNum = 5;

            for (Row row : employeeSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == firstNameColNum)
                            firstName = cellValue;
                        else if (cell.getColumnIndex() == secondNameColNum)
                            secondName = cellValue;
                        else if (cell.getColumnIndex() == shopColNum && shopRepo.findByAddress(cellValue).size() != 0)
                            shop = shopRepo.findByAddress(cellValue).get(0);
                        else if (cell.getColumnIndex() == isActiveColNum)
                            isActive = cellValue.equalsIgnoreCase("працюючий");
                    }
                if (firstName != null && !firstName.isBlank() && secondName != null && !secondName.isBlank() &&
                        shop != null) {
                    var newEmployee = new Employee(firstName, secondName, shop, isActive);
                    newEmployees.add(newEmployee);

                    shop = null;
                }
            }
            workbook.close();
            return newEmployees;
        } else
            throw new IllegalArgumentException();
    }
}