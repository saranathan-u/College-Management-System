package com.example.college_management.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String saveFile(MultipartFile file, String uploadDir) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.contains("..")) {
            throw new RuntimeException("Invalid file name: " + fileName);
        }
        try {
            Path dirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dirPath);
            Path targetPath = dirPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName + ". Error: " + e.getMessage());
        }
    }

    public static File getFile(String uploadDir, String fileName) {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        return filePath.toFile();
    }
}
