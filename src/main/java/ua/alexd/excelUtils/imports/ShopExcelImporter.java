package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import ua.alexd.domain.Shop;
import ua.alexd.domain.ShopDomain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class ShopExcelImporter {
    @NotNull
    public static List<Shop> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var shopSheet = workbook.getSheetAt(0);

        var typeTableFields = new String[]{"Id", "Адреса"};
        if (isValidTableStructure(shopSheet, typeTableFields)) {
            var dataFormatter = new DataFormatter();
            var newShops = new ArrayList<Shop>();

            var address = "";
            var addressColNum = 1;

            for (Row row : shopSheet) {
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