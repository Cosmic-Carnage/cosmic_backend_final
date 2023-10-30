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


    @Column
    private int like; // track the number of likes

    @Column
    private int dislike; // track the number of dislikes

    public String toString() {
        return "Product [id=" + id + ", image=" + image + "]";
    }
}
