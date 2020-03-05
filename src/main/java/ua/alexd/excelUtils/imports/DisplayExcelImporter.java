package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.controller.DisplayController;
import ua.alexd.domain.Display;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class DisplayExcelImporter extends Importer {
    @NotNull
    @Override
    public List<Display> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var displaySheet = workbook.getSheetAt(0);

        var displayTableFields = new String[]{"Id", "Модель", "Тип", "Діагональ", "Розширення"};
        if (isValidTableStructure(displaySheet, displayTableFields)) {
            var dataFormatter = new DataFormatter();
            var newDisplays = new ArrayList<Display>();

            String model = null;
            var modelColNum = 1;
            String type = null;
            var typeColNum = 2;
            String diagonal = null;
            var diagonalColNum = 3;
            String resolution = null;
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
                if (!DisplayController.isFieldsEmpty(model, type, diagonal, resolution)) {
                    var newDisplay = new Display(model, type, diagonal, resolution);
                    newDisplays.add(newDisplay);

                    nullExtractedValues(model, type, diagonal, resolution);
                }
            }
            workbook.close();
            return newDisplays;
        } else
            throw new IllegalArgumentException();
    }
}