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
        quiz3.setQuestion("What is the largest moon of Jupiter?");
        quiz3.setCorrectAnswer('c');
        quiz3.setAnswerA("Europa");
        quiz3.setAnswerB("Amalthea");
        quiz3.setAnswerC("Ganymede");
        quiz3.setAnswerD("Io");
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
        quiz5.setQuestion("What is the largest moon of Jupiter?");
        quiz5.setCorrectAnswer('c');
        quiz5.setAnswerA("Europa");
        quiz5.setAnswerB("Amalthea");
        quiz5.setAnswerC("Ganymede");
        quiz5.setAnswerD("Io");
        quiz5.setPoints(10);
        questionList.add(quiz5);

        return questionList;
    }
}
