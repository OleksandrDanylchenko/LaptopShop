package ua.alexd.excelUtils.imports;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.alexd.domain.Availability;
import ua.alexd.domain.Laptop;
import ua.alexd.domain.Shop;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.ShopRepo;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.dateTimeUtils.DateFormatter.parseDate;
import static ua.alexd.dateTimeUtils.DateTimeChecker.isDateStartPrevDateEnd;
import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

@Service
public class AvailabilityExcelImporter extends Importer {
    private LaptopRepo laptopRepo;
    private ShopRepo shopRepo;

    public AvailabilityExcelImporter(LaptopRepo laptopRepo, ShopRepo shopRepo) {
        this.laptopRepo = laptopRepo;
        this.shopRepo = shopRepo;
    }

    @NotNull
    @Override
    public List<Availability> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var availabilitySheet = workbook.getSheetAt(0);

        var ssdTableFields = new String[]{"Id", "Модель", "Ціна", "Кількість", "Номер магазину", "Адреса магазину",
                "Початок продаж", "Закінчення продаж"};
        if (isValidTableStructure(availabilitySheet, ssdTableFields)) {
            var dataFormatter = new DataFormatter();
            var newAvailabilities = new ArrayList<Availability>();

            Laptop laptop = null;
            var laptopColNum = 1;
            int price = 0;
            var priceColNum = 2;
            int quantity = 0;
            var quantityColNum = 3;
            Shop shop = null;
            var shopColNum = 5;
            Date dateStart = null;
            var dateStartColNum = 6;
            Date dateEnd = null;
            var dateEndColNum = 7;

            for (Row row : availabilitySheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == laptopColNum)
                            laptop = laptopRepo.findByLabelModel(cellValue);
                        else if (NumberUtils.isParsable(cellValue)) {
                            if (cell.getColumnIndex() == priceColNum)
                                price = Integer.parseInt(cellValue);
                            else if (cell.getColumnIndex() == quantityColNum)
                                quantity = Integer.parseInt(cellValue);
                        } else if (cell.getColumnIndex() == shopColNum && shopRepo.findByAddress(cellValue).size() != 0)
                            shop = shopRepo.findByAddress(cellValue).get(0);
                        else if (cell.getColumnIndex() == dateStartColNum)
                            try {
                                dateStart = parseDate(cellValue);
                            } catch (ParseException | ArrayIndexOutOfBoundsException ignored) { }
                        else if (cell.getColumnIndex() == dateEndColNum)
                            try {
                                dateEnd = parseDate(cellValue);
                            } catch (ParseException | ArrayIndexOutOfBoundsException ignored) { }
                    }
                if (laptop != null && price >= 5000 && quantity >= 1 && shop != null &&
                        dateStart != null && dateEnd != null && !isDateStartPrevDateEnd(dateStart, dateEnd)) {
                    var newAvailability = new Availability(quantity, price, dateStart, dateEnd, shop, laptop);
                    newAvailabilities.add(newAvailability);

                    nullExtractedValues(laptop, shop, dateStart, dateEnd);
                }
            }
            workbook.close();
            return newAvailabilities;
        } else
            throw new IllegalArgumentException();
    }
}