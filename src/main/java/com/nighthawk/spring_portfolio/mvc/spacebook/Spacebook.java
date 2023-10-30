package com.nighthawk.spring_portfolio.mvc.spacebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Configuration

public class ModelInit {
    @Autowired
    private JokesJpaRepository jokesRepo;
    @Autowired
    private LeaderboardJpaRepository leaderboardRepo;
    @Autowired
    private SpacebookJpaRepository spacebookRepo;
    @Autowired
    private NoteJpaRepository noteRepo;
    @Autowired
    private PersonDetailsService personService;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // Your initialization logic here
        };
    }
}

@Entity
public class Spacebook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String spacebook;

    private Integer like;
    private Integer dislike;

    @Lob
    private byte[] image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpacebook() {
        return spacebook;
    }

    public void setSpacebook(String spacebook) {
        this.spacebook = spacebook;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}