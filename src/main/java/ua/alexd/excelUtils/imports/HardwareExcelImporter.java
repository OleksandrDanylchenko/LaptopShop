package ua.alexd.excelUtils.imports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.alexd.domain.*;
import ua.alexd.repos.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.alexd.excelUtils.imports.TableValidator.isValidTableStructure;
import static ua.alexd.inputUtils.inputValidator.stringContainsAlphabet;

@Service
public class HardwareExcelImporter extends Importer {
    private CPURepo cpuRepo;
    private RAMRepo ramRepo;
    private SSDRepo ssdRepo;
    private DisplayRepo displayRepo;
    private HDDRepo hddRepo;
    private GPURepo gpuRepo;

    public HardwareExcelImporter(CPURepo cpuRepo, RAMRepo ramRepo, SSDRepo ssdRepo,
                                 DisplayRepo displayRepo, HDDRepo hddRepo, GPURepo gpuRepo) {
        this.cpuRepo = cpuRepo;
        this.ramRepo = ramRepo;
        this.ssdRepo = ssdRepo;
        this.displayRepo = displayRepo;
        this.hddRepo = hddRepo;
        this.gpuRepo = gpuRepo;
    }

    @NotNull
    @Override
    public List<Hardware> importFile(String uploadedFilePath)
            throws IOException, IllegalArgumentException {
        var workbook = WorkbookFactory.create(new File(uploadedFilePath));
        var hardwareSheet = workbook.getSheetAt(0);

        var hardwareTableFields = new String[]{"Id", "Назва збірки", "Модель процесора", "Модель відеокарти",
                "Модель дисплею", "Модель оперативної пам'яті", "Модель SSD диску", "Модель HDD диску"};
        if (isValidTableStructure(hardwareSheet, hardwareTableFields)) {
            var dataFormatter = new DataFormatter();
            var newHardware = new ArrayList<Hardware>();

            String assemblyName = null;
            var assemblyNameColNum = 1;
            CPU cpu = null;
            var cpuColNum = 2;
            GPU gpu = null;
            var gpuColNum = 3;
            Display display = null;
            var displayColNum = 4;
            RAM ram = null;
            var ramColNum = 5;
            SSD ssd = null;
            var ssdColNum = 6;
            HDD hdd = null;
            var hddColNum = 7;

            for (Row row : hardwareSheet) {
                if (row.getRowNum() != 0)
                    for (Cell cell : row) {
                        var cellValue = dataFormatter.formatCellValue(cell);
                        if (cell.getColumnIndex() == assemblyNameColNum)
                            assemblyName = cellValue;
                        else if (cell.getColumnIndex() == cpuColNum)
                            cpu = cpuRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == gpuColNum)
                            gpu = gpuRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == displayColNum)
                            display = displayRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == ramColNum)
                            ram = ramRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == ssdColNum)
                            ssd = ssdRepo.findByModel(cellValue);
                        else if (cell.getColumnIndex() == hddColNum)
                            hdd = hddRepo.findByModel(cellValue);
                    }
                if (stringContainsAlphabet(assemblyName) && cpu != null && gpu != null &&
                        display != null && ram != null && ssd != null && hdd != null) {
                    var newAssembly = new Hardware(assemblyName, cpu, gpu, ram, ssd, hdd, display);
                    newHardware.add(newAssembly);

                    nullExtractedValues(assemblyName, cpu, gpu, display, ram, ssd, hdd);
                }
            }
            workbook.close();
            return newHardware;
        } else
            throw new IllegalArgumentException();
    }
}
