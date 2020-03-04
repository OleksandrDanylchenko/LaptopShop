package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Type;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class TypeExcelImporter {
    @NotNull
    public static List<Type> importTypesFromExcel(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var sheet = workbook.getSheetAt(0);

        var typeTableFields = new String[]{"Id", "Назва"};
        if (isValidTableStructure(sheet, typeTableFields)) {
            var dataFormatter = new DataFormatter();
            var newTypes = new ArrayList<Type>();

            var name = "";
            var nameColNum = 1;

            for (Row row : sheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row)
                        if (cell.getColumnIndex() == nameColNum)
                            name = dataFormatter.formatCellValue(cell);

                if (name != null && !name.isBlank()) {
                    var newType = new Type(name);
                    newTypes.add(newType);
                }
            }
            return newTypes;
        } else
            throw new IllegalArgumentException();
    }
}