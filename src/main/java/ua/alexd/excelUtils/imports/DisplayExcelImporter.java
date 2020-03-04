package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Display;
import ua.alexd.domain.SSD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class DisplayExcelImporter {
    @NotNull
    public static List<Display> importFiles(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var displaySheet = workbook.getSheetAt(0);

        var displayTableFields = new String[]{"Id", "Модель", "Тип", "Діагональ", "Розширення"};
        if (isValidTableStructure(displaySheet, displayTableFields)) {
            var dataFormatter = new DataFormatter();
            var newDisplays = new ArrayList<Display>();

            var model = "";
            var modelColNum = 1;
            var type = "";
            var typeColNum = 2;
            var diagonal = "";
            var diagonalColNum = 3;
            var resolution = "";
            var resolutionColNum = 4;

            for (Row row : displaySheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            model = cellValue;
                        else if (cell.getColumnIndex() == typeColNum)
                            type = cellValue;
                        else if (cell.getColumnIndex() == diagonalColNum)
                            diagonal = cellValue;
                        else if (cell.getColumnIndex() == resolutionColNum)
                            resolution = cellValue;
                    }
                if (model != null && !model.isBlank() && type != null && !type.isBlank() &&
                        diagonal != null && !diagonal.isBlank() &&
                        resolution != null && !resolution.isBlank()) {
                    var newDisplay = new Display(model, type, diagonal, resolution);
                    newDisplays.add(newDisplay);
                }
            }
            workbook.close();
            return newDisplays;
        } else
            throw new IllegalArgumentException();
    }
}