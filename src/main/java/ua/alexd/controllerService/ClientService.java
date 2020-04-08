package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.dateTimeService.DateTimeChecker;
import ua.alexd.domain.Client;
import ua.alexd.excelInteraction.imports.ClientExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.ClientRepo;

import java.io.IOException;
import java.sql.Date;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.ClientSpecification.*;

@Service
@Lazy
public class ClientService {
    private final ClientRepo clientRepo;
    private final DateTimeChecker timeChecker;

    private final ClientExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public ClientService(ClientRepo clientRepo, DateTimeChecker timeChecker, ClientExcelImporter excelImporter,
                         UploadedFilesManager filesManager) {
        this.clientRepo = clientRepo;
        this.timeChecker = timeChecker;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Client> loadClientTable(String firstName, String secondName, Date dateReg) {
        var clientSpecification = createClientSpecification(firstName, secondName, dateReg);
        return clientRepo.findAll(clientSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Client> createClientSpecification(String firstName, String secondName, Date dateReg) {
        if (timeChecker.isNonValidDate(dateReg))
            dateReg = null;
        return Specification.where(firstNameEqual(firstName))
                .and(secondNameEqual(secondName)).and(dateRegEqual(dateReg));
    }

    public void addClientRecord(Client newClient) {
        clientRepo.save(newClient);
    }

    public void editClientRecord(String firstName, String secondName, Date dateReg, @NotNull Client editClient) {
        editClient.setFirstName(firstName);
        editClient.setSecondName(secondName);
        editClient.setDateReg(dateReg);
        clientRepo.save(editClient);
    }

    public boolean importExcelRecords(@NotNull @RequestParam MultipartFile uploadingFile) {
        var clientFilePath = "";
        try {
            clientFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newClients = excelImporter.importFile(clientFilePath);
            newClients.forEach(clientRepo::save);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(clientFilePath);
            return false;
        }
    }

    public void deleteRecord(Client delClient) {
        clientRepo.delete(delClient);
    }
}