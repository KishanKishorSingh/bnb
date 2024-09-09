package com.airbnb.controller;

import com.airbnb.entity.FileUpload;
import com.airbnb.repository.FileUploadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final FileUploadRepository fileUploadRepository;

    public ImageController(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("image")MultipartFile image,@RequestParam String description) throws IOException {
        FileUpload fileUpload=new FileUpload();
        fileUpload.setImage(image.getBytes());
        fileUpload.setDescription(description);
        return new ResponseEntity<>(fileUploadRepository.save(fileUpload), HttpStatus.CREATED);
    }
    @GetMapping("/get")
    public ResponseEntity<?> getImage(){
        List<FileUpload> all = fileUploadRepository.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }
}
