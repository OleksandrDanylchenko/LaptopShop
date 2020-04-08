package ua.alexd.excelInteraction.imports;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Lazy
public class UploadedFilesManager {
    @NotNull
    public String saveUploadingFile(@NotNull MultipartFile uploadFile)
            throws IOException, IllegalArgumentException {
        if (isUploadFileValid(uploadFile)) {
            var programPath = Paths.get(System.getProperty("user.dir"));
            var uploadPath = programPath.resolve("uploadedExcelFiles");
            var uploadDir = new File(uploadPath.toString());
            if (!uploadDir.exists())
                //noinspection ResultOfMethodCallIgnored
                uploadDir.mkdir();

            var uuidFile = UUID.randomUUID().toString();
            var resultFilename = uploadPath.toString() + '\\' + uuidFile + '.' + uploadFile.getOriginalFilename();

            uploadFile.transferTo(new File(resultFilename));

            return resultFilename;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isUploadFileValid(@NotNull MultipartFile uploadFile) {
        var uploadFilename = uploadFile.getOriginalFilename();
        var uploadFileType = uploadFile.getContentType();
        return uploadFilename != null && uploadFilename.length() >= 6 &&
                uploadFileType != null && uploadFileType.equals("application/octet-stream"); // has .xlsx extension
    }

    public static void deleteNonValidFile(String delFilepath) {
        var deletionFile = new File(delFilepath);
        //noinspection ResultOfMethodCallIgnored
        deletionFile.delete();
    }
}