package ua.alexd.excelUtils.imports;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadedFilesManager {
    public static void saveUploadingFile(MultipartFile uploadFile) throws IOException {
        var uploadPath = "D:\\Studying\\2_Course\\ISTAP\\LaptopShop\\uploadedExcelFiles";
        var uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            //noinspection ResultOfMethodCallIgnored
            uploadDir.mkdir();

        var uuidFile = UUID.randomUUID().toString();
        var resultFilename = uuidFile + '.' + uploadFile.getOriginalFilename();

        uploadFile.transferTo(new File(uploadPath + '\\' + resultFilename));
    }
}