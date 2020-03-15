package ua.alexd.excelUtils.imports;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.alexd.domain.CPU;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;
import static ua.alexd.inputUtils.inputValidator.stringContainsAlphabet;

@Service
@Lazy
public class CPUExcelImporter {
    private static DecimalFormat frequencyFormat = new DecimalFormat("#.#");

    @NotNull
    public List<CPU> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var cpuSheet = workbook.getSheetAt(0);

        var cpuTableFields = new String[]{"Id", "Модель", "Частота(GHz)"};
        if (isValidTableStructure(cpuSheet, cpuTableFields)) {
            var dataFormatter = new DataFormatter();
            var newCPUs = new ArrayList<CPU>();

            var modelColNum = 1;
            var frequencyColNum = 2;

            for (Row row : cpuSheet) {
                if (row.getRowNum() != 0) {
                    String cpuModel = null;
                    String frequency = null;

                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == modelColNum)
                            cpuModel = cellValue;
                        else if (cell.getColumnIndex() == frequencyColNum && NumberUtils.isParsable(cellValue))
                            frequency = frequencyFormat.format(Double.parseDouble(cellValue))
                                    .replace(',', '.');
                    }
                    addNewCPU(cpuModel, frequency, newCPUs);
                }
            }
            workbook.close();
            return newCPUs;
        } else
            throw new IllegalArgumentException();
    }

    private static void addNewCPU(String cpuModel, String frequency, ArrayList<CPU> newCPUs) {
        if (stringContainsAlphabet(cpuModel)) {
            var newCPU = new CPU(cpuModel, frequency);
            newCPUs.add(newCPU);
        }
    }
}