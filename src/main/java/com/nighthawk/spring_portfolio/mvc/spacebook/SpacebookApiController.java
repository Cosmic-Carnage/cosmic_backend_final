package com.nighthawk.spring_portfolio.mvc.spacebook;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SpacebookApiAppl {
	

    @Autowired
    private SpacebookJpaRepository uploadFileRepository;

	public void save(Spacebook image) {
        uploadFileRepository.save(image);
    }
    
}