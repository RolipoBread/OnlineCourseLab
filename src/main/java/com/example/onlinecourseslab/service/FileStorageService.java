package com.example.onlinecourseslab.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir =
        System.getProperty("user.dir") + "/uploads/avatars";

    public String save(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Empty file");
            }

            // уникальное имя файла
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // создаём папку если её нет
            Path dirPath = Paths.get(uploadDir);
            Files.createDirectories(dirPath);

            // путь к файлу
            Path filePath = dirPath.resolve(fileName);

            // сохраняем файл
            Files.copy(file.getInputStream(), filePath);

            // возвращаем URL для фронта
            return "/uploads/avatars/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}