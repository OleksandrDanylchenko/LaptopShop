package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Label;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class LabelExcelImporter {
    @NotNull
    public List<Label> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var labelSheet = workbook.getSheetAt(0);

        var labelTableFields = new String[]{"Id", "Бренд", "Модель"};
        if (isValidTableStructure(labelSheet, labelTableFields)) {
            var dataFormatter = new DataFormatter();
            var newLabels = new ArrayList<Label>();

            var brand = "";
            var brandColNum = 1;
            var model = "";
            var modelColNum = 2;

            for (Row row : labelSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == brandColNum)
                            brand = cellValue;
                        else if (cell.getColumnIndex() == modelColNum)
                            model = cellValue;
                    }
                if (brand != null && !brand.isBlank() && model != null && !model.isBlank()) {
                    var newLabel = new Label(brand, model);
                    newLabels.add(newLabel);
                }
            }
            workbook.close();
            return newLabels;
        } else
            throw new IllegalArgumentException();
    }
}