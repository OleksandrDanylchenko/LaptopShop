package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.controller.HDDController;
import ua.alexd.domain.HDD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class HDDExcelImporter extends Importer {
    @NotNull
    @Override
    public List<HDD> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var hddSheet = workbook.getSheetAt(0);

        var hddTableFields = new String[]{"Id", "Модель", "Обсяг пам'яті"};
        if (isValidTableStructure(hddSheet, hddTableFields)) {
            var dataFormatter = new DataFormatter();
            var newHDDs = new ArrayList<HDD>();

            String hddModel = null;
            var modelColNum = 1;
            int memory = 0;
            var memoryColNum = 2;

            for (Row row : hddSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            hddModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum)
                            try {
                                memory = Integer.parseInt(cellValue);
                            } catch (NumberFormatException ignored) {
                            }
                    }
                if (!HDDController.isFieldsEmpty(hddModel) && memory >= 1) {
                    var newHDD = new HDD(hddModel, memory);
                    newHDDs.add(newHDD);

                    nullExtractedValues(hddModel);
                }
            }
            workbook.close();
            return newHDDs;
        } else
            throw new IllegalArgumentException();
    }
}
