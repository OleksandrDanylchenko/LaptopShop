package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Buying;
import ua.alexd.domain.Laptop;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.LaptopRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

@Service
public class BuyingExcelImporter extends Importer {
    private BasketRepo basketRepo;
    private LaptopRepo laptopRepo;

    public BuyingExcelImporter(BasketRepo basketRepo, LaptopRepo laptopRepo) {
        this.basketRepo = basketRepo;
        this.laptopRepo = laptopRepo;
    }

    @NotNull
    @Override
    public List<Buying> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var buyingSheet = workbook.getSheetAt(0);

        var buyingTableFields = new String[]{"Id", "Загальна ціна", "Id кошика", "Час покупки",
                "Id ноутбуку", "Модель ноутбуку"};
        if (isValidTableStructure(buyingSheet, buyingTableFields)) {
            var dataFormatter = new DataFormatter();
            var newBuyings = new ArrayList<Buying>();

            int totalPrice = 0;
            var totalPriceColNum = 1;
            Basket basket = null;
            var basketIdColNum = 2;
            Laptop laptop = null;
            var laptopIdColNum = 4;

            for (Row row : buyingSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == totalPriceColNum)
                            try {
                                totalPrice = Integer.parseInt(cellValue);
                            } catch (NumberFormatException ignored) {
                            }
                        else if (cell.getColumnIndex() == basketIdColNum) {
                            try {
                                var basketId = Integer.parseInt(cellValue);
                                if (basketRepo.findById(basketId).isPresent())
                                    basket = basketRepo.findById(basketId).get();
                            } catch (NumberFormatException ignored) {
                            }
                        } else if (cell.getColumnIndex() == laptopIdColNum) {
                            try {
                                var laptopId = Integer.parseInt(cellValue);
                                if (laptopRepo.findById(laptopId).isPresent())
                                    laptop = laptopRepo.findById(laptopId).get();
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                if (totalPrice >= 5000 && laptop != null && basket != null) {
                    var newBuying = new Buying(totalPrice, laptop, basket);
                    newBuyings.add(newBuying);

                    nullExtractedValues(basket, laptop);
                }
            }
            workbook.close();
            return newBuyings;
        } else
            throw new IllegalArgumentException();
    }
}