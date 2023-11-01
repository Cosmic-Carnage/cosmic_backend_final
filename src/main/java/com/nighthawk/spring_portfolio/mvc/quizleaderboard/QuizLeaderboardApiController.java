package com.nighthawk.spring_portfolio.mvc.quizleaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizleaders")
public class QuizLeaderboardApiController {

    @Autowired
    private QuizLeaderboardJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<QuizLeaderboard>> getLeaderboard() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/addScore/{id}/{score}")
    public ResponseEntity<QuizLeaderboard> addScore(@PathVariable long id, @PathVariable int score) {
        Optional<QuizLeaderboard> optional = repository.findById(id);
        if (optional.isPresent()) {
            QuizLeaderboard leaders = optional.get();
            leaders.setScore(leaders.getScore() + score);
            repository.save(leaders);
            return new ResponseEntity<>(leaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<QuizLeaderboard> deleteLeaderboard(@PathVariable long id) {
        Optional<QuizLeaderboard> optional = repository.findById(id);
        if (optional.isPresent()) {
            QuizLeaderboard leaders = optional.get();
            repository.deleteById(id);
            return new ResponseEntity<>(leaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping( "/post")
public ResponseEntity<Object> postQuizLeaderboard(@RequestParam("username") String username,
                                                  @RequestParam("score") int score) {
        // A person object WITHOUT ID will create a new record with default roles as student
        QuizLeaderboard quizLeaderboard = new QuizLeaderboard(null, username, score);
        return new ResponseEntity<>(" is created successfully", HttpStatus.CREATED);
    }
}