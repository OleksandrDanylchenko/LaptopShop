package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.SSD;
import ua.alexd.domain.ShopDomain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class SSDExcelImporter {
    @NotNull
    public List<SSD> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var ssdSheet = workbook.getSheetAt(0);

        var ssdTableFields = new String[]{"Id", "Модель", "Обсяг пам'яті"};
        if (isValidTableStructure(ssdSheet, ssdTableFields)) {
            var dataFormatter = new DataFormatter();
            var newSSDs = new ArrayList<SSD>();

            var ssdModel = "";
            var modelColNum = 1;
            var ssdMemory = 0;
            var memoryColNum = 2;

            for (Row row : ssdSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            ssdModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum)
                            try {
                                ssdMemory = Integer.parseInt(cellValue);
                            } catch (NumberFormatException ignored) { }
                    }
                if (ssdModel != null && !ssdModel.isBlank() && ssdMemory >= 1) {
                    var newSSD = new SSD(ssdModel, ssdMemory);
                    newSSDs.add(newSSD);
                }
            }
            workbook.close();
            return newSSDs;
        } else
            throw new IllegalArgumentException();
    }
}