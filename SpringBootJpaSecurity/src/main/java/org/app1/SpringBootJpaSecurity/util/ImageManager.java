package org.app1.SpringBootJpaSecurity.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageManager implements FileManager {

    private String DIRECTORY_PATH = null;

    public ImageManager() {
    }

    //загрузка файла на диск
    @Override
    public void upload(byte[] bytes, String key) throws IOException {

        Path path = Paths.get(DIRECTORY_PATH, key);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(bytes);
        } finally {
            stream.close();
        }
    }

    //скачивание файла с диска
    @Override
    public Resource download(String key) throws IOException {

        Path path = Paths.get(DIRECTORY_PATH + "\\" + key);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException();
        }
    }

    //удаление файла с диска
    @Override
    public void delete(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + "\\" + key);
        Files.delete(path);
    }

    @Override
    public String getDirectoryPath() {
        return DIRECTORY_PATH;
    }

    @Autowired
    @Override
    public void setDirectoryPath(@Value("${file-storage.path}") String directoryPath) {
        if (DIRECTORY_PATH == null) {
            DIRECTORY_PATH = Paths.get(new File("").getAbsolutePath(), directoryPath).toString();
        }
    }
}
