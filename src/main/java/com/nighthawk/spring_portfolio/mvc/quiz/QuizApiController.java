package com.nighthawk.spring_portfolio.mvc.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizApiController {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizApiController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    // Get a list of all quiz questions
    @GetMapping
    public List<Quiz> getAllQuizQuestions() {
        return quizRepository.findAll();
    }

    // Get a specific quiz question by ID
    @GetMapping("/{id}")
    public Quiz getQuizQuestionById(@PathVariable Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    // Create a new quiz question
    @PostMapping
    public Quiz createQuizQuestion(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Update an existing quiz question
    @PutMapping("/{id}")
    public Quiz updateQuizQuestion(@PathVariable Long id, @RequestBody Quiz updatedQuiz) {
        return quizRepository.findById(id)
                .map(quiz -> {
                    quiz.setQuestion(updatedQuiz.getQuestion());
                    quiz.setCorrectAnswer(updatedQuiz.getCorrectAnswer());
                    quiz.setAnswerA(updatedQuiz.getAnswerA());
                    quiz.setAnswerB(updatedQuiz.getAnswerB());
                    quiz.setAnswerC(updatedQuiz.getAnswerC());
                    quiz.setAnswerD(updatedQuiz.getAnswerD());
                    quiz.setPoints(updatedQuiz.getPoints());
                    return quizRepository.save(quiz);
                })
                .orElse(null);
    }

    // Delete a quiz question by ID
    @DeleteMapping("/{id}")
    public void deleteQuizQuestion(@PathVariable Long id) {
        quizRepository.deleteById(id);
    }
}
