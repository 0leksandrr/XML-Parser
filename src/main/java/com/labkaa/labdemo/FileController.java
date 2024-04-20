package com.labkaa.labdemo;

import com.labkaa.labdemo.model.UploadedFile;
import com.labkaa.labdemo.repo.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Створення каталогу для завантажених файлів, якщо він не існує
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Генерування унікального імені файлу
            String fileName = file.getOriginalFilename();

            // Збереження файлу на сервер
            byte[] bytes = file.getBytes();
            File uploadedFile = new File(UPLOAD_DIR + fileName);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, bytes);

            // Збереження імені файлу в базі даних
            UploadedFile uploadedFileInfo = new UploadedFile();
            uploadedFileInfo.setFileName(fileName);
            uploadedFileRepository.save(uploadedFileInfo);

            // Виклик парсера для обробки завантаженого файлу
            Parser.parseXML(uploadedFile);

            return "Файл успішно завантажено і оброблено.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Помилка під час завантаження або обробки файлу.";
        }
    }

    @GetMapping("/files")
    public List<String> getAllFiles() {
        List<UploadedFile> files = uploadedFileRepository.findAll();
        List<String> fileNames = files.stream().map(UploadedFile::getFileName).collect(Collectors.toList());
        return fileNames;
    }
}

