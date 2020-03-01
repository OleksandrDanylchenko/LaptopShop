package ua.alexd.excelView;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.ShopDomain;

import java.util.List;

public interface ExcelFileStructure {
    int idColumnWidth = 5*256;
    int dataColumnWidth = 25 * 256;

    void setExcelHeader(@NotNull Workbook workbook, @NotNull Sheet excelSheet);

    void setExcelRows(@NotNull Workbook workbook, @NotNull Sheet excelSheet, List<ShopDomain> rows);
}