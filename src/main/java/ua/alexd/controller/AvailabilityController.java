package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Availability;
import ua.alexd.excelInteraction.imports.AvailabilityExcelImporter;
import ua.alexd.repos.AvailabilityRepo;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.ShopRepo;

import java.io.IOException;
import java.sql.Date;

import static ua.alexd.dateTimeService.DateTimeChecker.isDateStartPrevDateEnd;
import static ua.alexd.dateTimeService.DateTimeChecker.isNonValidDate;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.AvailabilitySpecification.*;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {
    private final AvailabilityRepo availabilityRepo;
    private static Iterable<Availability> lastOutputtedAvailabilities;

    private final LaptopRepo laptopRepo;
    private final ShopRepo shopRepo;

    private final AvailabilityExcelImporter excelImporter;

    public AvailabilityController(AvailabilityRepo availabilityRepo, LaptopRepo laptopRepo, ShopRepo shopRepo,
                                  AvailabilityExcelImporter excelImporter) {
        this.availabilityRepo = availabilityRepo;
        this.laptopRepo = laptopRepo;
        this.shopRepo = shopRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) Integer price,
                              @RequestParam(required = false) Integer quantity,
                              @RequestParam(required = false) String laptopModel,
                              @RequestParam(required = false) String shopAddress,
                              @RequestParam(required = false, defaultValue = "0001-01-01") Date dateStart,
                              @RequestParam(required = false, defaultValue = "0001-01-01") Date dateEnd,
                              @NotNull Model model) {
        if (isNonValidDate(dateStart))
            dateStart = null;
        if (isNonValidDate(dateEnd))
            dateEnd = null;
        var availabilitySpecification = Specification.where(fullPriceEqual(price)).and(quantityEqual(quantity))
                .and(laptopModelLike(laptopModel)).and(shopAddressLike(shopAddress)).and(dateStartEqual(dateStart))
                .and(dateEndEqual(dateEnd));
        var availabilities = availabilityRepo.findAll(availabilitySpecification);
        model.addAttribute("availabilities", availabilities);
        lastOutputtedAvailabilities = availabilities;
        return "/list/availabilityList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        initializeDropDownChoices(model);
        return "add/availabilityAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam Integer price, @RequestParam Integer quantity,
                             @RequestParam String laptopModel, @RequestParam String shopAddress,
                             @RequestParam Date dateStart,
                             @RequestParam Date dateEnd,
                             @NotNull Model model) {
        if (isDateStartPrevDateEnd(dateStart, dateEnd)) {
            model.addAttribute("errorMessage",
                    "Дата закінчення продаж не може передувати даті початку продаж");
            initializeDropDownChoices(model);
            return "add/availabilityAdd";
        }

        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var shop = shopRepo.findByAddress(shopAddress).get(0);
        var newAvailability = new Availability(quantity, price, dateStart, dateEnd, shop, laptop);
        if (!saveRecord(newAvailability, model))
            return "add/availabilityAdd";

        return "redirect:/availability";
    }

    @NotNull
    @GetMapping("/edit/{editAvailability}")
    private String editRecord(@NotNull @PathVariable Availability editAvailability, @NotNull Model model) {
        model.addAttribute("editAvailability", editAvailability);
        initializeDropDownChoices(model);
        return "/edit/availabilityEdit";
    }

    @NotNull
    @PostMapping("/edit/{editAvailability}")
    private String editRecord(@RequestParam Integer price, @RequestParam Integer quantity,
                              @RequestParam String laptopModel, @RequestParam String shopAddress,
                              @RequestParam Date dateStart, @RequestParam Date dateEnd,
                              @PathVariable Availability editAvailability, @NotNull Model model) {
        if (isDateStartPrevDateEnd(dateStart, dateEnd)) {
            model.addAttribute("errorMessage",
                    "Дата закінчення продаж не може передувати даті початку продаж");
            return "/edit/availabilityEdit";
        }

        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editAvailability.setLaptop(laptop);

        var shop = shopRepo.findByAddress(shopAddress).get(0);
        editAvailability.setShop(shop);

        editAvailability.setPrice(price);
        editAvailability.setQuantity(quantity);
        editAvailability.setDateStart(dateStart);
        editAvailability.setDateEnd(dateEnd);

        if (!saveRecord(editAvailability, model))
            return "edit/availabilityEdit";

        return "redirect:/availability";
    }

    @NotNull
    @GetMapping("/importExcel")
    private String importExcel(@NotNull Model model) {
        initializeImportAttributes(model);
        return "excel/excelFilesUpload";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var uploadedFilePath = "";
        try {
            uploadedFilePath = saveUploadingFile(uploadingFile);
            var newAvailabilities = excelImporter.importFile(uploadedFilePath);
            newAvailabilities.forEach(newAvailability -> saveRecord(newAvailability, model));
            return "redirect:/availability";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(uploadedFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл таблиці записів про наявність!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Availability.class.getSimpleName());
        model.addAttribute("tableName", "записів про наявність");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("availabilities", lastOutputtedAvailabilities);
        return "availabilityExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delAvailability}")
    private String deleteRecord(@NotNull @PathVariable Availability delAvailability) {
        availabilityRepo.delete(delAvailability);
        return "redirect:/availability";
    }

    private boolean saveRecord(Availability saveAvailability, Model model) {
        try {
            availabilityRepo.save(saveAvailability);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель ноутбуку " + saveAvailability.getLaptop().getLabel().getModel()
                            + " уже присутня в базі");
            initializeDropDownChoices(model);
            return false;
        }
        return true;
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("shopAddresses", shopRepo.getAllAddresses());
    }
}