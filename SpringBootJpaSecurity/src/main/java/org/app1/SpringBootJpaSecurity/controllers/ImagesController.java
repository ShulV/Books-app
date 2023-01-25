package org.app1.SpringBootJpaSecurity.controllers;

import org.app1.SpringBootJpaSecurity.models.PersonImageInfo;
import org.app1.SpringBootJpaSecurity.services.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImagesController {

    private final ImagesService imagesService;

    @Autowired
    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

//    @PostMapping("/")
//    public ResponseEntity<PersonImageInfo> upload(@RequestParam MultipartFile file) {
//        try {
//            return new ResponseEntity<PersonImageInfo>(imagesService.upload(file), HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<PersonImageInfo>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws IOException {
        Optional<PersonImageInfo> foundFile = imagesService.findById(id);
        if (foundFile.isPresent()) {
            Resource resource = imagesService.download(foundFile.get().getKey());
            return ResponseEntity.ok()
                    .header("Content-Disposition",
                            "file; filename=" +
                                    foundFile.get().getName())
                    .body(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            imagesService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
