package ua.alexd.excelView;

import org.apache.poi.ss.usermodel.Sheet;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.ShopDomain;

import java.util.List;

public interface ExcelFileStructure {
    void setExcelHeader(@NotNull Sheet excelSheet);

    void setExcelRows(@NotNull Sheet excelSheet, List<ShopDomain> rows);
}