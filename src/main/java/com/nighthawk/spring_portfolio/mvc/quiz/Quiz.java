package com.nighthawk.spring_portfolio.mvc.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String question;

    private char correctAnswer;

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    private int points;

    public static HashMap<String, Integer> init() {
        HashMap<String, Integer> leaders = new HashMap<>();
        {
            leaders.put("Tay", 20);
        }
        return leaders;
    }

    public static List<String> init2() {
        List<String> questionList = new ArrayList<>();

        questionList.add("What is the largest moon of Jupiter?");
        questionList.add("What is the closest star to our solar system?");
        questionList.add("What planet is the Great Red Spot on?");
        questionList.add("What is the largest known galaxy?");
        questionList.add("Who was the first human to walk on the Moon?");

        questionList.add("Europa, Amalthea, Ganymede, Io, c");
        questionList.add("Polaris, Betelgeuse, Altair, Proxima Centauri, d");
        questionList.add("Earth, Jupiter, Mars, Neptune, b");
        questionList.add("Alcyoneus, IC 1101, Milky Way, Andromeda, a");
        questionList.add("Alan Shepard, John Glenn, Yuri Gagarin, Neil Armstrong, d");

        return questionList;
    }
}
