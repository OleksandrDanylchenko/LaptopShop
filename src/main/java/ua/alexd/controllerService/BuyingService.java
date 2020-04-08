package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Buying;
import ua.alexd.excelInteraction.imports.BuyingExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.BuyingRepo;
import ua.alexd.repos.LaptopRepo;

import java.io.IOException;
import java.time.LocalDateTime;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.BuyingSpecification.*;

@Service
@Lazy
public class BuyingService {
    private final BuyingRepo buyingRepo;

    private final BasketRepo basketRepo;
    private final LaptopRepo laptopRepo;

    private final BuyingExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public BuyingService(BuyingRepo buyingRepo, BasketRepo basketRepo, LaptopRepo laptopRepo,
                         BuyingExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.buyingRepo = buyingRepo;
        this.basketRepo = basketRepo;
        this.laptopRepo = laptopRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Buying> loadBuyingTable(Integer basketId, String laptopModel, Integer totalPrice,
                                            LocalDateTime dateTime, Model model) {
        var buyingSpecification = createBuyingSpecification(basketId, laptopModel, totalPrice, dateTime);
        var buyings = buyingRepo.findAll(buyingSpecification);
        initializeBuyingChoices(model);
        return buyings;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Buying> createBuyingSpecification(Integer basketId, String laptopModel, Integer totalPrice,
                                                            LocalDateTime dateTime) {
        return Specification.where(basketIdEqual(basketId)).and(laptopModelEqual(laptopModel))
                .and(totalPriceEqual(totalPrice)).and(dateTimeEqual(dateTime));
    }

    public void addBuyingRecord(Integer basketId, String laptopModel, Integer totalPrice) {
        Basket basket = null;
        if (basketRepo.findById(basketId).isPresent())
            basket = basketRepo.findById(basketId).get();
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var newBuying = new Buying(totalPrice, laptop, basket);
        buyingRepo.save(newBuying);
    }

    public void editBuyingRecord(@RequestParam Integer basketId, @RequestParam String laptopModel,
                                 @RequestParam Integer totalPrice, @PathVariable Buying editBuying) {
        Basket basket = null;
        if (basketRepo.findById(basketId).isPresent())
            basket = basketRepo.findById(basketId).get();
        editBuying.setBasket(basket);
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editBuying.setLaptop(laptop);
        editBuying.setTotalPrice(totalPrice);
        buyingRepo.save(editBuying);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initializeBuyingChoices(model);
        var buyingFilePath = "";
        try {
            buyingFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newBuyings = excelImporter.importFile(buyingFilePath);
            newBuyings.forEach(buyingRepo::save);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(buyingFilePath);
            return false;
        }
    }

    public void deleteRecord(Buying delBuying) {
        buyingRepo.delete(delBuying);
    }

    private void initializeBuyingChoices(@NotNull Model model) {
        model.addAttribute("basketIds", basketRepo.getAllIds())
                .addAttribute("laptopModels", laptopRepo.getAllModels());
    }
}