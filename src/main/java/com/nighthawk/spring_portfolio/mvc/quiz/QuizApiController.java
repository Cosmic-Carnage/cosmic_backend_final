package com.nighthawk.spring_portfolio.mvc.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizApiController {

    @GetMapping("/")
    public ResponseEntity<List<String>> getQuiz() {
        List<String> questions = Quiz.init2();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}