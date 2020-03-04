package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.SSD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class SSDExcelImport {
    private static DataFormatter dataFormatter = new DataFormatter();

    @NotNull
    public static List<SSD> importSSDsFromExcel(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var sheet = workbook.getSheetAt(0);

        var ssdTableFields = new String[]{"Id", "Модель", "Обсяг пам'яті"};
        if (isValidTableStructure(sheet, ssdTableFields)) {
            var newSSDs = new ArrayList<SSD>();

            var model = "";
            var modelColNum = 1;
            var memory = 0;
            var memoryColNum = 2;

            for (Row row : sheet) {
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
                    var newSSD = new SSD(model, memory);
                    newSSDs.add(newSSD);
                }
            }
            workbook.close();
            return newSSDs;
        } else
            throw new IllegalArgumentException();
    }
}