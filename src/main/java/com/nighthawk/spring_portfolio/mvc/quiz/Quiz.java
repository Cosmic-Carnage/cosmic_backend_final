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

    public static List<Quiz> init() {
        List<Quiz> questionList = new ArrayList<>();

        Quiz quiz1 = new Quiz();
        quiz1.setQuestion("What is the largest moon of Jupiter?");
        quiz1.setCorrectAnswer('c');
        quiz1.setAnswerA("Europa");
        quiz1.setAnswerB("Amalthea");
        quiz1.setAnswerC("Ganymede");
        quiz1.setAnswerD("Io");
        quiz1.setPoints(10);
        questionList.add(quiz1);

        Quiz quiz2 = new Quiz();
        quiz2.setQuestion("What is the closest star to our solar system?");
        quiz2.setCorrectAnswer('d');
        quiz2.setAnswerA("Polaris");
        quiz2.setAnswerB("Betelgeuse");
        quiz2.setAnswerC("Altair");
        quiz2.setAnswerD("Proxima Centauri");
        quiz2.setPoints(10);
        questionList.add(quiz2);

        Quiz quiz3 = new Quiz();
        quiz3.setQuestion("Wnat planet is the Great Red Spot on?");
        quiz3.setCorrectAnswer('b');
        quiz3.setAnswerA("Earth");
        quiz3.setAnswerB("Jupyter");
        quiz3.setAnswerC("Mars");
        quiz3.setAnswerD("Neptune");
        quiz3.setPoints(10);
        questionList.add(quiz3);

        Quiz quiz4 = new Quiz();
        quiz4.setQuestion("What is the largest known galaxy?");
        quiz4.setCorrectAnswer('a');
        quiz4.setAnswerA("Alcyoneus");
        quiz4.setAnswerB("IC 1101");
        quiz4.setAnswerC("Milky Way");
        quiz4.setAnswerD("Andromeda");
        quiz4.setPoints(10);
        questionList.add(quiz4);

        Quiz quiz5 = new Quiz();
        quiz5.setQuestion("Who was the first human to walk on the Moon?");
        quiz5.setCorrectAnswer('d');
        quiz5.setAnswerA("Alan Shepard");
        quiz5.setAnswerB("John Glenn");
        quiz5.setAnswerC("Yuri Gagarin");
        quiz5.setAnswerD("Neil Armstrong");
        quiz5.setPoints(10);
        questionList.add(quiz5);

        return questionList;
    }
}
