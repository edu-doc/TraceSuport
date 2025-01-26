package br.edu.ufersa.tracesuport.TraceSuport.domain.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        return "";
    }

    public static String saveFile(String path, MultipartFile file) throws IOException {
        String uniqueFileName = LocalDate.now() + "-" + UUID.randomUUID().toString() + "." + FileUtils.getFileExtension(file.getOriginalFilename());

        String filePath = path + File.separator + uniqueFileName; 

        File directory = new File(System.getProperty("user.dir") + path);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + filePath); 

        fout.write(file.getBytes()); 

        fout.close();

        return filePath;
    }
}
