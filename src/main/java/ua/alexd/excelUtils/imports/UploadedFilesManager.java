package ua.alexd.excelUtils.imports;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadedFilesManager {
    @NotNull
    public static String saveUploadingFile(@NotNull MultipartFile uploadFile)
            throws IOException, IllegalArgumentException {
        if (isUploadFileXSLX(uploadFile)) {
            var uploadPath = "D:\\Studying\\2_Course\\ISTAP\\LaptopShop\\uploadedExcelFiles";
            var uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                //noinspection ResultOfMethodCallIgnored
                uploadDir.mkdir();

            var uuidFile = UUID.randomUUID().toString();
            var resultFilename = uploadPath + '\\' + uuidFile + '.' + uploadFile.getOriginalFilename();

            uploadFile.transferTo(new File(resultFilename));

            return resultFilename;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isUploadFileXSLX(@NotNull MultipartFile uploadFile) {
        var uploadFilename = uploadFile.getOriginalFilename();
        var uploadFileType = uploadFile.getContentType();
        return uploadFilename != null && uploadFilename.length() >= 6 &&
                uploadFileType != null && uploadFileType.equals("application/octet-stream");
    }

    public static void deleteNonValidFile(String delFilepath) {
        var deletionFile = new File(delFilepath);
        //noinspection ResultOfMethodCallIgnored
        deletionFile.delete();
    }
}