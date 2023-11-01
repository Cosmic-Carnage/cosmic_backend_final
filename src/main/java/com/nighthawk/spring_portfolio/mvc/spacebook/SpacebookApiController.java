
package com.nighthawk.spring_portfolio.mvc.spacebook;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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

    @PostMapping
    public ResponseEntity<String> save(MultipartFile image, @RequestParam("fileName") String fileName) throws IOException {
        String encodedString = Base64.getEncoder().encodeToString(image.getBytes());
        Spacebook file = new Spacebook(fileName, encodedString);
        uploadFileRepository.save(file);
        return new ResponseEntity<>("Image has been uploaded", HttpStatus.CREATED);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        Optional<Spacebook> optional = uploadFileRepository.findByfileName(fileName);
        if (optional.isPresent()) {
            Spacebook file = optional.get();
            String data = file.getImageEncoder();
            byte[] imageBytes = Base64.getDecoder().decode(data);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<Spacebook> setUpVote(@PathVariable long id) {
        Optional<Spacebook> optional = uploadFileRepository.findById(id);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get(); 
            spacebook.setLike(spacebook.getLike()+1);
            uploadFileRepository.save(spacebook); 
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
    @PostMapping("/dislike/{id}")
    public ResponseEntity<Spacebook> setDownVote(@PathVariable long id) {
        Optional<Spacebook> optional = uploadFileRepository.findById(id);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get();
            spacebook.setDislike(spacebook.getDislike()+1);
            uploadFileRepository.save(spacebook);
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Spacebook> deleteSpacebook(@PathVariable long id) {
        Optional<Spacebook> optional = uploadFileRepository.findById(id);
        if (optional.isPresent()) {
            Spacebook spacebook = optional.get();
            uploadFileRepository.deleteById(id);
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
