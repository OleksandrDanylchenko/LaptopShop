package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Type;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;
import static ua.alexd.inputUtils.inputValidator.stringContainsAlphabet;

public class TypeExcelImporter extends Importer {
    @NotNull
    @Override
    public List<Type> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var typeSheet = workbook.getSheetAt(0);

        var typeTableFields = new String[]{"Id", "Назва"};
        if (isValidTableStructure(typeSheet, typeTableFields)) {
            var dataFormatter = new DataFormatter();
            var newTypes = new ArrayList<Type>();

            String name = null;
            var nameColNum = 1;

            for (Row row : typeSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row)
                        if (cell.getColumnIndex() == nameColNum)
                            name = dataFormatter.formatCellValue(cell);

                if (stringContainsAlphabet(name)) {
                    var newType = new Type(name);
                    newTypes.add(newType);

                    nullExtractedValues(name);
                }
            }
            return newTypes;
        } else
            throw new IllegalArgumentException();
    }
}