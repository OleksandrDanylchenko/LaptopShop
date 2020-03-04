package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.alexd.domain.Hardware;
import ua.alexd.domain.Label;
import ua.alexd.domain.Laptop;
import ua.alexd.domain.Type;
import ua.alexd.repos.HardwareRepo;
import ua.alexd.repos.LabelRepo;
import ua.alexd.repos.TypeRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;

public class LaptopExcelImporter {
    @NotNull
    public static List<Laptop> importFile(String uploadedFilePath,
                                          LabelRepo labelRepo, TypeRepo typeRepo, HardwareRepo hardwareRepo)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var laptopSheet = workbook.getSheetAt(0);

        var laptopTableFields = new String[]{"Id", "Бренд", "Модель", "Тип", "Збірка"};
        if (isValidTableStructure(laptopSheet, laptopTableFields)) {
            var dataFormatter = new DataFormatter();
            var newLaptops = new ArrayList<Laptop>();

            Label label = null;
            var labelColNum = 2;
            Type type = null;
            var typeColNum = 3;
            Hardware hardware = null;
            var hardwareColNum = 4;

            for (Row row : laptopSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == labelColNum)
                            label = labelRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == typeColNum && typeRepo.findByName(cellValue).size() != 0)
                            type = typeRepo.findByName(cellValue).get(0);
                        else if (cell.getColumnIndex() == hardwareColNum)
                            hardware = hardwareRepo.findByAssemblyName(cellValue);
                    }
                if (label != null && type != null && hardware != null) {
                    var newLaptop = new Laptop(label, type, hardware);
                    newLaptops.add(newLaptop);

                    label = null;
                    type = null;
                    hardware = null;
                }
            }
            workbook.close();
            return newLaptops;
        } else
            throw new IllegalArgumentException();
    }
}