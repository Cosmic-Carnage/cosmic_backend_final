package com.nighthawk.spring_portfolio.mvc.spacebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.ui.Model;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.File;


@RestController
@RequestMapping("/api/spacebook")
public class SpacebookApiController {

    @Autowired
    private SpacebookJpaRepository spacebookRepo;

    @GetMapping("/")
    public ResponseEntity<List<Spacebook>> getSpacebook() {
        return new ResponseEntity<>(spacebookRepo.findAll(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> saveSpacebookToDB(@RequestParam("file") String file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] imageBytes = Base64.getDecoder().decode(file);
            Spacebook spacebook = new Spacebook();
            spacebook.setImage(imageBytes);

            Spacebook savedSpacebook = spacebookRepo.save(spacebook);

            return new ResponseEntity<>("Image uploaded successfully. Spacebook ID: " + savedSpacebook.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Upload failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> serveImage(@PathVariable("id") Long id) {
        Optional<Spacebook> optional = spacebookRepo.findById(id);
        if (optional.isPresent()) {
            Spacebook spacebook = optional.get();
            byte[] imageBytes = spacebook.getImage();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set the content type to image/jpeg or your file type
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<Spacebook> setUpVote(@PathVariable long id) {
        Optional<Spacebook> optional = spacebookRepo.findById(id);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get(); 
            spacebook.setLike(spacebook.getLike()+1);
            spacebookRepo.save(spacebook); 
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
    @PostMapping("/dislike/{id}")
    public ResponseEntity<Spacebook> setDownVote(@PathVariable long id) {
        Optional<Spacebook> optional = spacebookRepo.findById(id);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get();
            spacebook.setDislike(spacebook.getDislike()+1);
            spacebookRepo.save(spacebook);
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Spacebook> deleteSpacebook(@PathVariable long id) {
        Optional<Spacebook> optional = spacebookRepo.findById(id);
        if (optional.isPresent()) {
            Spacebook spacebook = optional.get();
            spacebookRepo.deleteById(id);
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}