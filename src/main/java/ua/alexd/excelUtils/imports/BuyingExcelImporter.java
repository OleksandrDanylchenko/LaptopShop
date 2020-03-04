package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Buying;
import ua.alexd.domain.SSD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class BuyingExcelImporter {
    @NotNull
    public static List<Buying> importSSDsFromExcel(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var buyingSheet = workbook.getSheetAt(0);

        var buyingTableFields = new String[]{"Id", "Загальна ціна", "Id кошика", "Час покупки",
                "Id ноутбуку", "Модель ноутбуку"};
        if (isValidTableStructure(buyingSheet, buyingTableFields)) {
            var dataFormatter = new DataFormatter();
            var newSSDs = new ArrayList<Buying>();

            var model = "";
            var modelColNum = 1;
            var memory = 0;
            var memoryColNum = 2;

            for (Row row : buyingSheet) {
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
//                    newSSDs.add(newSSD);
                }
            }
            workbook.close();
            return newSSDs;
        } else
            throw new IllegalArgumentException();
    }
}