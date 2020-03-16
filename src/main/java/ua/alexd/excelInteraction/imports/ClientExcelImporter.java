package ua.alexd.excelInteraction.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.alexd.domain.Client;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.dateTimeService.DateFormatter.parseDate;
import static ua.alexd.excelInteraction.imports.TableValidator.isValidTableStructure;
import static ua.alexd.inputService.InputValidator.stringContainsAlphabet;

@Service
@Lazy
public class ClientExcelImporter {
    @NotNull
    public List<Client> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var clientSheet = workbook.getSheetAt(0);

        var ssdTableFields = new String[]{"Id", "Ім'я", "Прізвище", "Дата реєстрації"};
        if (isValidTableStructure(clientSheet, ssdTableFields)) {
            var dataFormatter = new DataFormatter();
            var newClients = new ArrayList<Client>();

            var firstNameColNum = 1;
            var secondNameColNum = 2;
            var dateRegColNum = 3;

            for (Row row : clientSheet) {
                if (row.getRowNum() != 0) {
                    String firstName = null;
                    String secondName = null;
                    Date dateReg = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == firstNameColNum)
                            firstName = cellValue;
                        else if (cell.getColumnIndex() == secondNameColNum)
                            secondName = cellValue;
                        else if (cell.getColumnIndex() == dateRegColNum)
                            try {
                                dateReg = parseDate(cellValue);
                            } catch (ParseException | ArrayIndexOutOfBoundsException ignored) {
                            }
                    }
                    addNewClient(firstName, secondName, dateReg, newClients);
                }
            }
            workbook.close();
            return newClients;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewClient(String firstName, String secondName, Date dateReg,
                                     ArrayList<Client> newClients) {
        if (stringContainsAlphabet(firstName) && stringContainsAlphabet(secondName) && dateReg != null) {
            var newClient = new Client(firstName, secondName, dateReg);
            newClients.add(newClient);
        }
    }
}