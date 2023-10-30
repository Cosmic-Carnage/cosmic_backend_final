package com.nighthawk.spring_portfolio.mvc.spacebook;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class Spacebook {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String image;
    public String getImage() {
		return image;
	}

    public void setImage(String image) {
		this.image = image;
	}


    @Column
    private int like; // track the number of likes
    public String getLike() {
		return image;
	}

    public void setLike(String image) {
		this.image = image;
	}

    @Column
    private int dislike; // track the number of dislikes
    public String getDislike() {
		return image;
	}

    public void setDislike(String image) {
		this.image = image;
	}


    public String toString() {
        return "Product [id=" + id + ", image=" + image + "]";
    }
}
