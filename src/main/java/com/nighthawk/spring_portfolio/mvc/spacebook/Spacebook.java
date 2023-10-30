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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spacebook {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private String imageEncoder;

    @Column
    private int like; // track the number of likes

    @Column
    private int dislike; // track the number of dislikes

    public Spacebook(String username, String imageEncoder) {
        this.username = username;
        this.imageEncoder = imageEncoder;
        this.like = 0; // initialize likes to 0
        this.dislike = 0; // initialize dislikes to 0
    }
}
