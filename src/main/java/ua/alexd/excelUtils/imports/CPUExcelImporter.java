package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.controller.CPUController;
import ua.alexd.domain.CPU;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class CPUExcelImporter {
    @NotNull
    public static List<CPU> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var cpuSheet = workbook.getSheetAt(0);

        var cpuTableFields = new String[]{"Id", "Модель", "Частота(GHz)"};
        if (isValidTableStructure(cpuSheet, cpuTableFields)) {
            var dataFormatter = new DataFormatter();
            var newCPUs = new ArrayList<CPU>();

            var cpuModel = "";
            var modelColNum = 1;
            var frequency = "";
            var frequencyColNum = 2;

            for (Row row : cpuSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            cpuModel = cellValue;
                        else if (cell.getColumnIndex() == frequencyColNum)
                            try {
                                Double.parseDouble(cellValue); // to prohibit any character in frequency value
                                frequency = cellValue;
                            } catch (NumberFormatException ignored) {
                                frequency = null;
                            }
                    }
                if (!CPUController.isFieldsEmpty(cpuModel, frequency)) {
                    var newCPU = new CPU(cpuModel, frequency);
                    newCPUs.add(newCPU);
                }
            }
            workbook.close();
            return newCPUs;
        } else
            throw new IllegalArgumentException();
    }
}