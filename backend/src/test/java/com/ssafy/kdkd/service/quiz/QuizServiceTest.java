package com.ssafy.kdkd.service.quiz;

import com.ssafy.kdkd.domain.common.Category;
import com.ssafy.kdkd.domain.entity.quiz.Quiz;
import com.ssafy.kdkd.repository.quiz.QuizRepository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.ssafy.kdkd.domain.entity.quiz.Quiz.createQuiz;
import static org.junit.Assert.*;

import jakarta.persistence.EntityManager;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class QuizServiceTest {

    @Autowired QuizRepository quizRepository;
    @Autowired QuizService quizService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("퀴즈 생성")
    @Transactional
    public void 퀴즈_생성() throws Exception {
        //given
        long size = 100L;
        for (int i = 0; i < size; i++) {
            createQuizList();
        }
        em.flush();

        //when
        long count = quizRepository.count();

        //then
        assertEquals("생성된 퀴즈 갯수는 100개 입니다.", size, count);
    }

    @Test
    @DisplayName("퀴즈 생성 & 조회")
    @Transactional
    public void 퀴즈_생성_조회() throws Exception {
        //given
        long size = 100L;
        for (int i = 0; i < size; i++) {
            createQuizList();
        }
        em.flush();

        //when
        List<Quiz> findQuizList = quizService.findAll();
        int findCount = findQuizList.size();

        System.out.println("===== 퀴즈 출력 =====");
        for (Quiz quiz : findQuizList) {
            System.out.println("quiz_id: "+ quiz.getId() +
                " category: " + quiz.getCategory() +
                " question: " + quiz.getQuestion() +
                " answer: " + quiz.isAnswer());
        }
        System.out.println("===== 퀴즈 출력 =====");

        //then
        assertEquals("조회된 퀴즈 갯수는 2개 입니다.", 2, findCount);
    }

    private Quiz createQuizList() {
        Category category = Category.values()[ThreadLocalRandom.current().nextInt(Category.values().length)];
        String question = "비트코인의 가치는 어떻게 결정되나요?";
        boolean answer = ThreadLocalRandom.current().nextBoolean();
        Quiz quiz = createQuiz(category, question, answer);
        em.persist(quiz);
        return quiz;
    }

}
