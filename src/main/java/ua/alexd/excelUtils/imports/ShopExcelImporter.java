package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Shop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class ShopExcelImporter {
    private static DataFormatter dataFormatter = new DataFormatter();

    @NotNull
    public static List<Shop> importShopsFromExcel(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var sheet = workbook.getSheetAt(0);

        var typeTableFields = new String[]{"Id", "Адреса"};
        if (isValidTableStructure(sheet, typeTableFields)) {
            var newShops = new ArrayList<Shop>();

            String address = null;
            int addressColNum = 1;

            for (Row row : sheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row)
                        if (cell.getColumnIndex() == addressColNum)
                            address = dataFormatter.formatCellValue(cell);

                if (address != null && !address.isBlank()) {
                    var newShop = new Shop(address);
                    newShops.add(newShop);
                }
            }
            workbook.close();
            return newShops;
        } else
            throw new IllegalArgumentException();
    }
}