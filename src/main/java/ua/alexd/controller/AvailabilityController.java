package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
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

import static ua.alexd.dateTimeService.DateTimeChecker.isNonValidDate;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.AvailabilitySpecification.*;

@Controller
@RequestMapping("/availability")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
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
    public String getRecords(@RequestParam(required = false) Integer price,
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
        lastOutputtedAvailabilities = availabilities;
        model.addAttribute("availabilities", availabilities);
        initializeDropDownChoices(model);
        return "view/availability/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@RequestParam Integer price, @RequestParam Integer quantity,
                             @RequestParam String laptopModel, @RequestParam String shopAddress,
                             @RequestParam Date dateStart,
                             @RequestParam Date dateEnd,
                             @NotNull Model model) {
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var shop = shopRepo.findByAddress(shopAddress).get(0);
        var newAvailability = new Availability(quantity, price, dateStart, dateEnd, shop, laptop);
        if (!saveRecord(newAvailability)) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель ноутбуку уже присутня в записах про наявність!");
            initializeDropDownChoices(model);
            model.addAttribute("availabilities", lastOutputtedAvailabilities);
            return "view/availability/table";
        }
        return "redirect:/availability";
    }

    @NotNull
    @PostMapping("/edit/{editAvailability}")
    public String editRecord(@RequestParam Integer editPrice, @RequestParam Integer editQuantity,
                              @RequestParam String editLaptopModel, @RequestParam String editShopAddress,
                              @RequestParam Date editDateStart, @RequestParam Date editDateEnd,
                              @NotNull @PathVariable Availability editAvailability, @NotNull Model model) {
        var laptop = laptopRepo.findByLabelModel(editLaptopModel);
        editAvailability.setLaptop(laptop);

        var shop = shopRepo.findByAddress(editShopAddress).get(0);
        editAvailability.setShop(shop);

        editAvailability.setPrice(editPrice);
        editAvailability.setQuantity(editQuantity);
        editAvailability.setDateStart(editDateStart);
        editAvailability.setDateEnd(editDateEnd);

        if (!saveRecord(editAvailability)) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель ноутбуку уже присутня в записах про наявність!");
            initializeDropDownChoices(model);
            model.addAttribute("availabilities", lastOutputtedAvailabilities);
            return "view/availability/table";
        }
        return "redirect:/availability";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var uploadedFilePath = "";
        try {
            uploadedFilePath = saveUploadingFile(uploadingFile);
            var newAvailabilities = excelImporter.importFile(uploadedFilePath);
            newAvailabilities.forEach(this::saveRecord);
            return "redirect:/availability";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(uploadedFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл таблиці записів про наявність!");
            initializeDropDownChoices(model);
            model.addAttribute("availabilities", lastOutputtedAvailabilities);
            return "view/availability/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("availabilities", lastOutputtedAvailabilities);
        return "availabilityExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delAvailability}")
    public String deleteRecord(@NotNull @PathVariable Availability delAvailability) {
        availabilityRepo.delete(delAvailability);
        return "redirect:/availability";
    }

    private boolean saveRecord(Availability saveAvailability) {
        try {
            availabilityRepo.save(saveAvailability);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("shopAddresses", shopRepo.getAllAddresses());
    }
}