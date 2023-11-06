package com.nighthawk.spring_portfolio.mvc.spacebook;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class SpacebookApiController {
    @Autowired
    private SpacebookJpaRepository uploadFileRepository;

    @Autowired
    SpacebookApiAppl imageService;

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/")
    public ResponseEntity<List<Spacebook>> getSpacebook() {
        return new ResponseEntity<>(uploadFileRepository.findAll(), HttpStatus.OK);
    }

    // handle http post request for saving an image
    @PostMapping
    public ResponseEntity<String> save(MultipartFile image, @RequestParam("fileName") String fileName) throws IOException {
        // encode the contents of the uploaded image file as a Base64-encoded string
        String encodedString = Base64.getEncoder().encodeToString(image.getBytes());
        Spacebook file = new Spacebook(fileName, encodedString);
        uploadFileRepository.save(file);
        return new ResponseEntity<>("Image has been uploaded", HttpStatus.CREATED);
    }

    // handle http get request for downloading an image by its filename
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        // check if the image with the given filename exists in the repository
        Optional<Spacebook> optional = uploadFileRepository.findByfileName(fileName);
        if (optional.isPresent()) {
            Spacebook file = optional.get();
            String data = file.getImageEncoder();
            byte[] imageBytes = Base64.getDecoder().decode(data);

            // determine the MediaType based on the file extension
            MediaType mediaType = MediaType.IMAGE_PNG; // default set to PNG
            if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (fileName.toLowerCase().endsWith(".gif")) {
                mediaType = MediaType.IMAGE_GIF;
            } 
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/like/{fileName}")
    public ResponseEntity<Spacebook> setUpVote(@PathVariable String fileName) {
        Optional<Spacebook> optional = uploadFileRepository.findByfileName(fileName);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get(); 
            spacebook.setLike(spacebook.getLike()+1);
            uploadFileRepository.save(spacebook); 
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
    @PostMapping("/dislike/{fileName}")
    public ResponseEntity<Spacebook> setDownVote(@PathVariable String fileName) {
        Optional<Spacebook> optional = uploadFileRepository.findByfileName(fileName);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get();
            spacebook.setDislike(spacebook.getDislike()+1);
            uploadFileRepository.save(spacebook);
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteSpacebook(@PathVariable String fileName) {
        Optional<Spacebook> optional = uploadFileRepository.findByfileName(fileName);
        if (optional.isPresent()) {
            Spacebook spacebook = optional.get();
            uploadFileRepository.deleteByfileName(fileName);
            return new ResponseEntity<String>("Image has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
