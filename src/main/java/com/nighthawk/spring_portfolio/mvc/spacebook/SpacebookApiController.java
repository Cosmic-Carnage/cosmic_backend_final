package com.nighthawk.spring_portfolio.mvc.spacebook;
 
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SpacebookApiController {
    @Autowired
    private spacebookRepository spacebookRepo;

    public void saveSpacebookToDB(MultipartFile file, String name, String description, int price) {
        Spacebook p = new Spacebook();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("Not a valid file");
        }
        try {
            // Encode the image file as a Base64 string and set it in the Spacebook entity
            p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setDescription(description);
        p.setName(name);
        p.setPrice(price);

        // Save the Spacebook entity to the database
        spacebookRepo.save(p);
    }

    public List<Spacebook> getAllSpacebook() {
        // Retrieve and return a list of all Spacebooks from the repository
        return spacebookRepo.findAll();
    }

    public void deleteSpacebookById(Long id) {
        // Delete a Spacebook by its ID
        spacebookRepo.deleteById(id);
    }

    public void changeSpacebookName(Long id, String name) {
        // Retrieve the Spacebook by ID, update the name, and save it back to the database
        Spacebook p = spacebookRepo.findById(id).orElse(null);
        if (p != null) {
            p.setName(name);
            spacebookRepo.save(p);
        }
    }

    public void changeSpacebookDescription(Long id, String description) {
        // Retrieve the Spacebook by ID, update the description, and save it back to the database
        Spacebook p = spacebookRepo.findById(id).orElse(null);
        if (p != null) {
            p.setDescription(description);
            spacebookRepo.save(p);
        }
    }

    public void changeSpacebookPrice(Long id, int price) {
        // Retrieve the Spacebook by ID, update the price, and save it back to the database
        Spacebook p = spacebookRepo.findById(id).orElse(null);
        if (p != null) {
            p.setPrice(price);
            spacebookRepo.save(p);
        }
    }
}
