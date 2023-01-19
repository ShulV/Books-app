package org.app1.SpringBootJpaSecurity.services;

import org.app1.SpringBootJpaSecurity.models.Person;
import org.app1.SpringBootJpaSecurity.models.PersonImage;
import org.app1.SpringBootJpaSecurity.repositories.PersonImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PersonImageService {

    @Value("${upload.path.person-images}")
    private String uploadPath;

    private final PersonImagesRepository personImagesRepository;

    @Autowired
    public PersonImageService(PersonImagesRepository personImagesRepository) {
        this.personImagesRepository = personImagesRepository;
    }

    //  добавить изображение на диск, а также в базу
    @Transactional
    public void addImage(MultipartFile file, Person person) throws IOException {

        String classPath = new ClassPathResource("").getFile().getAbsolutePath();
        PersonImage imageFile = new PersonImage();
        imageFile.setName(file.getOriginalFilename());
        imageFile.setSize((int) file.getSize());
        String uuidFile = UUID.randomUUID().toString();//получаем уникальный хэш
        String resultFileName = uuidFile + "." + file.getOriginalFilename();
        imageFile.setHashedName(resultFileName);

        imageFile.setPerson(person);
        personImagesRepository.save(imageFile);
        String pathName = classPath + uploadPath + "/" + resultFileName;
        file.transferTo(new File(pathName));
    }


}
