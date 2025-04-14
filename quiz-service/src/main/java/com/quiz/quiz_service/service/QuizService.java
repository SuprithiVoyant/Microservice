package com.quiz.quiz_service.service;

import com.quiz.quiz_service.entity.Quiz;
import com.quiz.quiz_service.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionClient questionClient;

    public Quiz add(Quiz quiz){
        return quizRepository.save(quiz);
    }

    public List<Quiz> get(){
        List<Quiz> quizzes = quizRepository.findAll();
        List<Quiz> newQuizzes = quizzes.stream().map(quiz -> {
            quiz.setQuestions(questionClient.getQuestionsOfQuiz(quiz.getId()));
            return quiz;
        }).collect(Collectors.toList());
        return newQuizzes;
    }

    public Quiz get(Long id){
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setQuestions(questionClient.getQuestionsOfQuiz(quiz.getId()));
        return quiz;
    }
}
