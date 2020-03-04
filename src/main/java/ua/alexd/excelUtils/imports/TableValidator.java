package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.jetbrains.annotations.NotNull;

public final class TableValidator {
    private static DataFormatter dataFormatter = new DataFormatter();

    public static boolean isValidTableStructure(@NotNull Sheet sheet, @NotNull String... tableFields) {
        if (tableFields.length != sheet.getRow(0).getPhysicalNumberOfCells())
            return false;
        for (int i = 0; i < tableFields.length; i++) {
            var typeHeaderCell = sheet.getRow(0).getCell(i);
            var typeHeaderCellValue = dataFormatter.formatCellValue(typeHeaderCell);
            if (!typeHeaderCellValue.equals(tableFields[i]))
                return false;
        }
        return true;
    }
}