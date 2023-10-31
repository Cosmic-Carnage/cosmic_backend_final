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
   
    public Long getId() {
      return id;
  }

    private String image;
    public String getImage() {
		return image;
	}

    public void setImage(String image) {
		this.image = image;
	}

    @Column
    private int like;
    @Column
    private int dislike; // track the number of dislikes

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String toString() {
        return "Product [id=" + id + ", image=" + image + "]";
    }
}
