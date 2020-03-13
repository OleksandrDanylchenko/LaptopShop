package ua.alexd.excelUtils.imports;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.alexd.domain.GPU;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;
import static ua.alexd.inputUtils.inputValidator.stringContainsAlphabet;

@Service
public class GPUExcelImporter {
    @NotNull
    public List<GPU> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var gpuSheet = workbook.getSheetAt(0);

        var gpuTableFields = new String[]{"Id", "Модель", "Обсяг пам'яті"};
        if (isValidTableStructure(gpuSheet, gpuTableFields)) {
            var dataFormatter = new DataFormatter();
            var newGPUs = new ArrayList<GPU>();

            var modelColNum = 1;
            var memoryColNum = 2;

            for (Row row : gpuSheet) {
                if (row.getRowNum() != 0) {
                    String gpuModel = null;
                    int gpuMemory = 0;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            gpuModel = cellValue;
                        else if (cell.getColumnIndex() == memoryColNum && NumberUtils.isParsable(cellValue))
                            gpuMemory = Integer.parseInt(cellValue);
                    }
                    addNewGPU(gpuModel, gpuMemory, newGPUs);
                }
            }
            workbook.close();
            return newGPUs;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewGPU(String gpuModel, int gpuMemory, ArrayList<GPU> newGPUs) {
        if (stringContainsAlphabet(gpuModel) && gpuMemory >= 1) {
            var newGPU = new GPU(gpuModel, gpuMemory);
            newGPUs.add(newGPU);
        }
    }
}