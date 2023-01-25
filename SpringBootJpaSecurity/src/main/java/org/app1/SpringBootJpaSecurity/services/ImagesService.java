package org.app1.SpringBootJpaSecurity.services;

import org.app1.SpringBootJpaSecurity.models.Person;
import org.app1.SpringBootJpaSecurity.models.PersonImageInfo;
import org.app1.SpringBootJpaSecurity.repositories.ImagesRepository;
import org.app1.SpringBootJpaSecurity.util.FileManager;
import org.app1.SpringBootJpaSecurity.util.ImageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ImagesService {

    @Value("${file-storage.path}")
    private String uploadPath;

    private final ImagesRepository imagesRepository;
    private final FileManager imageManager;

    @Autowired
    public ImagesService(ImagesRepository imagesRepository, FileManager imageManager) {
        this.imagesRepository = imagesRepository;
        this.imageManager = imageManager;
    }

    @Transactional(rollbackFor = {IOException.class})
    public PersonImageInfo upload(MultipartFile file, Person person) throws IOException {
        String key = generateKey(file.getOriginalFilename());
        PersonImageInfo createdFile = new PersonImageInfo();
        createdFile.setName(file.getOriginalFilename());
        createdFile.setKey(key);
        createdFile.setSize((int) file.getSize());
        createdFile.setDate(LocalDate.now());
        createdFile.setPerson(person);

        imagesRepository.save(createdFile);
        imageManager.upload(file.getBytes(), key);

        return createdFile;
    }

//    @Transactional(rollbackFor = {IOException.class})
//    public PersonImageInfo upload(MultipartFile file) throws IOException {
//        String key = generateKey(file.getOriginalFilename());
//        PersonImageInfo createdFile = new PersonImageInfo();
//        createdFile.setName(file.getOriginalFilename());
//        createdFile.setKey(key);
//        createdFile.setSize((int) file.getSize());
//        createdFile.setDate(LocalDate.now());
//
//        imagesRepository.save(createdFile);
//        imageManager.upload(file.getBytes(), key);
//
//        return createdFile;
//    }

    private String generateKey(String name) {
        return (UUID.randomUUID().toString() + LocalDate.now().toString() + name).trim();
    }

    public Optional<PersonImageInfo> findById(Long id) {
        return imagesRepository.findById(id);
    }

    public Resource download(String key) throws IOException {
        return imageManager.download(key);
    }

    @Transactional(rollbackFor = {IOException.class})
    public void delete(Long id) throws IOException {
        Optional<PersonImageInfo> file = imagesRepository.findById(id);
        if (file.isPresent()) {
            imagesRepository.delete(file.get());
            imageManager.delete(file.get().getKey());
        }

    }
}
