package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.HDD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class HDDExcelImporter {
    @NotNull
    public List<HDD> importSSDsFromExcel(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var hddSheet = workbook.getSheetAt(0);

        var hddTableFields = new String[]{"Id", "Модель", "Обсяг пам'яті"};
        if (isValidTableStructure(hddSheet, hddTableFields)) {
            var dataFormatter = new DataFormatter();
            var newHDDs = new ArrayList<HDD>();

            var model = "";
            var modelColNum = 1;
            var memory = 0;
            var memoryColNum = 2;

            for (Row row : hddSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            model = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum)
                            try {
                                memory = Integer.parseInt(cellValue);
                            } catch (NumberFormatException ignored) { }
                    }
                if (model != null && !model.isBlank() && memory >= 1) {
                    var newHDD = new HDD(model, memory);
                    newHDDs.add(newHDD);
                }
            }
            workbook.close();
            return newHDDs;
        } else
            throw new IllegalArgumentException();
    }
}
