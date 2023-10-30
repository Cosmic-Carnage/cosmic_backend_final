package com.nighthawk.spring_portfolio.mvc.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quiz")
public class QuizApiController {

    @Autowired
    private QuizJpaRepository quizRepository;

    @GetMapping("/")
    public ResponseEntity<List<String>> getQuiz() {
        List<String> questions = Quiz.init();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping("/updateScore/{id}/{points}")
    public ResponseEntity<Quiz> addScore(@PathVariable long id) {
        Optional<Quiz> optional = quizRepository.findById(id);
        if (optional.isPresent()) {
            Quiz quiz = optional.get();
            quiz.setPoints(quiz.getPoints() + 10);
            quizRepository.save(quiz);
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}