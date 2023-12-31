package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.jokes.Jokes;
import com.nighthawk.spring_portfolio.mvc.jokes.JokesJpaRepository;
import com.nighthawk.spring_portfolio.mvc.leaderboard.Leaderboard;
import com.nighthawk.spring_portfolio.mvc.leaderboard.LeaderboardJpaRepository;
import com.nighthawk.spring_portfolio.mvc.spacebook.SpacebookJpaRepository;
import com.nighthawk.spring_portfolio.mvc.quiz.Quiz;
import com.nighthawk.spring_portfolio.mvc.quiz.QuizJpaRepository;
import com.nighthawk.spring_portfolio.mvc.quizleaderboard.QuizLeaderboard;
import com.nighthawk.spring_portfolio.mvc.quizleaderboard.QuizLeaderboardJpaRepository;

import java.util.HashMap;
import java.util.List;

@Component
@Configuration
public class ModelInit {
    @Autowired
    JokesJpaRepository jokesRepo;
    @Autowired
    LeaderboardJpaRepository leaderboardRepo;
    @Autowired
    SpacebookJpaRepository spacebookRepo;
    @Autowired
    QuizJpaRepository quizRepo;
    @Autowired
    QuizLeaderboardJpaRepository quizLeadersRepo;


    @Bean
    CommandLineRunner run() {
        return args -> {
            //Joke database is populated with starting jokes
            String[] jokesArray = Jokes.init();
            for (String joke : jokesArray) {
                List<Jokes> jokeFound = jokesRepo.findByJokeIgnoreCase(joke);
                if (jokeFound.size() == 0)
                    jokesRepo.save(new Jokes(null, joke, 0, 0));
            }

            HashMap<String, Integer> leaderboardHash = Leaderboard.init();
            for (String leaderboard : leaderboardHash.keySet()) {
                List<Leaderboard> leaderboardFound = leaderboardRepo.findByLeaderboardIgnoreCase(leaderboard);
                if (leaderboardFound.size() == 0)
                    leaderboardRepo.save(new Leaderboard(null, leaderboard, leaderboardHash.get(leaderboard)));
            }

            List<String> questionList = Quiz.init2();
            for (String question : questionList) {
                List<Quiz> quizFound = quizRepo.findByQuestionIgnoreCase(question);
                if (quizFound.size() == 0) {
                    Quiz newQuiz = new Quiz();
                    newQuiz.setQuestion(question);
                    quizRepo.save(newQuiz);
                }
            }

            HashMap<String, Integer> leaders = QuizLeaderboard.init();
            for (String leader : leaders.keySet()) {
                List<QuizLeaderboard> leadersFound = quizLeadersRepo.findByLeadersIgnoreCase(leader);
                if (leadersFound.size() == 0) {
                    quizLeadersRepo.save(new QuizLeaderboard(null, leader, leaders.get(leader)));
                }
            }
        };
    }
}
