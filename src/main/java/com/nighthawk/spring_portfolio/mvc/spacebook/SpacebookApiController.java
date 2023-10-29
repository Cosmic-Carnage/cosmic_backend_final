package com.nighthawk.spring_portfolio.mvc.spacebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.File;

@RestController
@RequestMapping("/api/spacebook")
public class SpacebookApiController {

    @Autowired
    private SpacebookJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Spacebook>> getSpacebook() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<Spacebook> setUpVote(@PathVariable long id) {
        Optional<Spacebook> optional = repository.findById(id);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get(); 
            spacebook.setLike(spacebook.getLike()+1);
            repository.save(spacebook); 
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
    @PostMapping("/dislike/{id}")
    public ResponseEntity<Spacebook> setDownVote(@PathVariable long id) {
        Optional<Spacebook> optional = repository.findById(id);
        if (optional.isPresent()) { 
            Spacebook spacebook = optional.get();
            spacebook.setDislike(spacebook.getDislike()+1);
            repository.save(spacebook);
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Spacebook> deleteSpacebook(@PathVariable long id) {
        Optional<Spacebook> optional = repository.findById(id);
        if (optional.isPresent()) {
            Spacebook spacebook = optional.get();
            repository.deleteById(id);
            return new ResponseEntity<>(spacebook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private static final String UPLOAD_DIR = "uploads";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }

        try {
            // Generate a unique filename for the uploaded image
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Define the upload directory
            String uploadDir = UPLOAD_DIR;

            // Create the directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the uploaded file to the server
            File serverFile = new File(directory.getAbsolutePath() + File.separator + uniqueFileName);
            try (FileOutputStream fos = new FileOutputStream(serverFile)) {
                fos.write(file.getBytes());
            }

        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
        return "File uploaded successfully.";
    }
    // @PostMapping("/comment/{id}")
    // public ResponseEntity<Spacebook> setComment(@PathVariable String comment, @PathVariable long id) {
    //     Optional<Spacebook> optional = repository.findById(id);
    //     if (optional.isPresent()) {
    //         Spacebook spacebook = optional.get();
            
    //         // Assuming Spacebook has a List<Comment> field for storing comments
    //         List<String> comments = spacebook.getComments();
    //         comments.add(comment); // Add the new comment
    
    //         // Set the updated comments list back to the Spacebook
    //         spacebook.setComments(comments);
    
    //         repository.save(spacebook);
    
    //         return new ResponseEntity<>(spacebook, HttpStatus.OK);

    //     }
    //     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    
    // }
}