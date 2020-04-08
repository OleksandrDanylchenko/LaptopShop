package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.dateTimeService.DateTimeChecker;
import ua.alexd.domain.Availability;
import ua.alexd.excelInteraction.imports.AvailabilityExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.AvailabilityRepo;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.ShopRepo;

import java.io.IOException;
import java.sql.Date;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.AvailabilitySpecification.*;

@Service
@Lazy
public class AvailabilityService {
    private final AvailabilityRepo availabilityRepo;
    private final DateTimeChecker timeChecker;

    private final LaptopRepo laptopRepo;
    private final ShopRepo shopRepo;

    private final AvailabilityExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public AvailabilityService(AvailabilityRepo availabilityRepo, DateTimeChecker timeChecker,
                               LaptopRepo laptopRepo, ShopRepo shopRepo, AvailabilityExcelImporter excelImporter,
                               UploadedFilesManager filesManager) {
        this.availabilityRepo = availabilityRepo;
        this.timeChecker = timeChecker;
        this.laptopRepo = laptopRepo;
        this.shopRepo = shopRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Availability> loadAvailabilityTable(Integer price, Integer quantity, String laptopModel,
                                                        String shopAddress, Date dateStart, Date dateEnd, Model model) {
        var availabilitySpecification = createAvailabilitySpecification(
                price, quantity, laptopModel, shopAddress,
                dateStart, dateEnd);
        var availabilities = availabilityRepo.findAll(availabilitySpecification);
        initializeAvailabilityChoices(model);
        return availabilities;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Availability> createAvailabilitySpecification(
            Integer price, Integer quantity, String laptopModel, String shopAddress,
            Date dateStart, Date dateEnd) {
        if (timeChecker.isNonValidDate(dateStart))
            dateStart = null;
        if (timeChecker.isNonValidDate(dateEnd))
            dateEnd = null;
        return Specification.where(fullPriceEqual(price)).and(quantityEqual(quantity))
                .and(laptopModelLike(laptopModel)).and(shopAddressLike(shopAddress)).and(dateStartEqual(dateStart))
                .and(dateEndEqual(dateEnd));
    }

    public boolean addAvailabilityRecord(Integer price, Integer quantity, String laptopModel, String shopAddress,
                                         Date dateStart, Date dateEnd, Model model) {
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var shop = shopRepo.findByAddress(shopAddress).get(0);
        var newAvailability = new Availability(quantity, price, dateStart, dateEnd, shop, laptop);
        initializeAvailabilityChoices(model);
        return saveRecord(newAvailability);
    }

    public boolean editAvailabilityRecord(Integer price, Integer quantity, String laptopModel, String shopAddress,
                                          Date dateStart, Date dateEnd, @NotNull Availability editAvailability,
                                          Model model) {
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editAvailability.setLaptop(laptop);
        var shop = shopRepo.findByAddress(shopAddress).get(0);
        editAvailability.setShop(shop);
        editAvailability.setPrice(price);
        editAvailability.setQuantity(quantity);
        editAvailability.setDateStart(dateStart);
        editAvailability.setDateEnd(dateEnd);
        initializeAvailabilityChoices(model);
        return saveRecord(editAvailability);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initializeAvailabilityChoices(model);
        var uploadedFilePath = "";
        try {
            uploadedFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newAvailabilities = excelImporter.importFile(uploadedFilePath);
            newAvailabilities.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(uploadedFilePath);
            return false;
        }
    }

    private boolean saveRecord(Availability saveAvailability) {
        try {
            availabilityRepo.save(saveAvailability);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Availability delAvailability) {
        availabilityRepo.delete(delAvailability);
    }

    private void initializeAvailabilityChoices(@NotNull Model model) {
        model.addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("shopAddresses", shopRepo.getAllAddresses());
    }
}