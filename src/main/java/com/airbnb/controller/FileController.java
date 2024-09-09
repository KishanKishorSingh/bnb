package com.airbnb.controller;


import com.airbnb.entity.AppUser;
import com.airbnb.entity.ImageUpload;
import com.airbnb.entity.Property;
import com.airbnb.repository.ImageUploadRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.implementationClass.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

   private ImageUploadRepository imageUploadRepository ;
   private PropertyRepository propertyRepository ;
   private S3Service s3Service ;

    public FileController(ImageUploadRepository imageUploadRepository, PropertyRepository propertyRepository, S3Service s3Service) {
        this.imageUploadRepository = imageUploadRepository;
        this.propertyRepository = propertyRepository;
        this.s3Service = s3Service;
    }
    @PostMapping(value = "/upload/file/property/{propertyId}",consumes= MediaType.MULTIPART_FORM_DATA_VALUE
            ,produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file ,
                                        @PathVariable long propertyId,
                                        @AuthenticationPrincipal AppUser user) throws IOException {
        String fileUrl = s3Service.uploadFile(file);
        Property property = propertyRepository.findById(propertyId).get();
        ImageUpload imageUpload=new ImageUpload();
        imageUpload.setUrl(fileUrl);
        imageUpload.setProperty(property);
        ImageUpload save = imageUploadRepository.save(imageUpload);
        return new ResponseEntity<>(save, HttpStatus.CREATED);

    }
    @GetMapping(value = "/images/property/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ImageUpload>> getImagesByPropertyId(@PathVariable long propertyId) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        if (propertyOptional.isPresent()) {
            List<ImageUpload> images = imageUploadRepository.findByProperty(propertyOptional.get());
            return new ResponseEntity<>(images, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
