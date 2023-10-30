package com.nighthawk.spring_portfolio.mvc.spacebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.ui.Model;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.File;

import com.nighthawk.spring_portfolio.mvc.spacebook.SpacebookApiAppl;



@RestController
@RequestMapping("/api/spacebook")
public class SpacebookApiController {

    @Autowired
    private SpacebookJpaRepository repository;

    @Autowired
    private SpacebookApiAppl spacebookApiAppl;

    @GetMapping("/")
    public ResponseEntity<List<Spacebook>> getSpacebook() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/listProducts.html")
	public String showExampleView(Model model)
	{
		List<Spacebook> products = spacebookApiAppl.getAllSpacebook();
		model.addAttribute("products", products);
		return "/listProducts.html";
	}
    @GetMapping("/addProduct.html")
    public String showAddProduct()
    {
    	
    	return "/addProduct.html";
    }
    
    @PostMapping("/addP")
    public String saveProduct(@RequestParam("file") MultipartFile file,
    		@RequestParam("pname") String name,
    		@RequestParam("price") int price,
    		@RequestParam("desc") String desc)
    {
    	spacebookApiAppl.saveSpacebookToDB(file, name, desc, price);
    	return "redirect:/listProducts.html";
    }
    
    @GetMapping("/deleteProd/{id}")
    public String deleteProduct(@PathVariable("id") Long id)
    {
    	
    	spacebookApiAppl.deleteSpacebookById(id);
    	return "redirect:/listProducts.html";
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
}