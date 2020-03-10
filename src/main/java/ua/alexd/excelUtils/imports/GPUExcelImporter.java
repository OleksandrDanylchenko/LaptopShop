package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.controller.GPUController;
import ua.alexd.domain.GPU;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class GPUExcelImporter extends Importer {
    @NotNull
    @Override
    public List<GPU> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var gpuSheet = workbook.getSheetAt(0);

        var gpuTableFields = new String[]{"Id", "Модель", "Обсяг пам'яті"};
        if (isValidTableStructure(gpuSheet, gpuTableFields)) {
            var dataFormatter = new DataFormatter();
            var newGPUs = new ArrayList<GPU>();

            String gpuModel = null;
            var modelColNum = 1;
            int memory = 0;
            var memoryColNum = 2;

            for (Row row : gpuSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            gpuModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum)
                            try {
                                memory = Integer.parseInt(cellValue);
                            } catch (NumberFormatException ignored) { }
                    }
                if (!GPUController.isFieldsEmpty(gpuModel) && memory >= 1) {
                    var newGPU = new GPU(gpuModel, memory);
                    newGPUs.add(newGPU);
                }
            }
            workbook.close();
            return newGPUs;
        } else
            throw new IllegalArgumentException();
    }
}