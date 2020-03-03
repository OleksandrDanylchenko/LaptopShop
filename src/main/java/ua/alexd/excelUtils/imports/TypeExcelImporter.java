package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Type;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TypeExcelImporter {
    private static int typeFieldsNum = Type.class.getDeclaredFields().length;
    private static DataFormatter dataFormatter = new DataFormatter();

    @NotNull
    public static List<Type> importTypesFromExcel(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var sheet = workbook.getSheetAt(0);

        if (isValidTableStructure(sheet)) {
            var newTypes = new ArrayList<Type>();
            String name = null;
            int nameColNum = 1;

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

    private static boolean isValidTableStructure(@NotNull Sheet sheet) {
        if (typeFieldsNum != sheet.getRow(0).getPhysicalNumberOfCells())
            return false;
        else {
            var typeTableFields = new String[]{"Id", "Назва"};
            for (int i = 0; i < typeTableFields.length; i++) {
                var typeHeaderCell = sheet.getRow(0).getCell(i);
                var typeHeaderCellValue = dataFormatter.formatCellValue(typeHeaderCell);
                if (!typeHeaderCellValue.equals(typeTableFields[i]))
                    return false;
            }
        }
        return true;
    }
}