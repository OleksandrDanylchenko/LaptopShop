package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Shop;
import ua.alexd.excelInteraction.imports.ShopExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.ShopSpecification.addressLike;

@Service
public class ShopService {
    private final ShopRepo shopRepo;
    private final EmployeeRepo employeeRepo;

    private final ShopExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public ShopService(ShopRepo shopRepo, EmployeeRepo employeeRepo, ShopExcelImporter excelImporter,
                       UploadedFilesManager filesManager) {
        this.shopRepo = shopRepo;
        this.employeeRepo = employeeRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Shop> loadShopTable(String address) {
        var shopSpecification = createShopSpecification(address);
        return shopRepo.findAll(shopSpecification);
    }

    private Specification<Shop> createShopSpecification(String address) {
        return Specification.where(addressLike(address));
    }

    public boolean addShopRecord(Shop newShop) {
        return saveRecord(newShop);
    }

    public boolean editShopRecord(String editAddress, @NotNull Shop editShop) {
        editShop.setAddress(editAddress);
        return saveRecord(editShop);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var shopFilePath = "";
        try {
            shopFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newShops = excelImporter.importFile(shopFilePath);
            newShops.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(shopFilePath);
            return false;
        }
    }

    private boolean saveRecord(Shop saveShop) {
        try {
            shopRepo.save(saveShop);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Shop delShop) {
        dismissEmployees(delShop);
        shopRepo.delete(delShop);
    }

    private void dismissEmployees(@NotNull Shop delShop) {
        var employees = delShop.getShopEmployees();
        employees.forEach(e -> e.setActive(false));
        for (var employee : employees)
            employeeRepo.save(employee);
    }
}