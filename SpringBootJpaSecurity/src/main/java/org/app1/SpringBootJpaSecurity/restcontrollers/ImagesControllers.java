package org.app1.SpringBootJpaSecurity.restcontrollers;

import org.app1.SpringBootJpaSecurity.services.PersonImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImagesControllers {

    private final PersonImageService personImageService;

    @Autowired
    public ImagesControllers(PersonImageService personImageService) {
        this.personImageService = personImageService;
    }

//    @PostMapping("/uploadFile")
//    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) {
//        personImageService.
//    }
    
}
