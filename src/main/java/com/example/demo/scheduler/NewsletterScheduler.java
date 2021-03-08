package com.example.demo.scheduler;


import com.example.demo.model.dao.News;
import com.example.demo.model.dao.Quiz;
import com.example.demo.repository.GamesRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsletterScheduler {

    private final MailService mailService;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final QuizRepository quizRepository;
    private final GamesRepository gamesRepository;

    //@Scheduled(cron = "0 0 7 ? * MON")
    @Scheduled(cron = "0 45,50 9 ? * MON")
//    @Scheduled(fixedRate = 10000)
    public void sendNewsletter() {
        List<News> news = newsRepository.findByCreatedDateIsAfter(LocalDate.now().minusDays(7));
        List<Quiz> quiz = quizRepository.findByCreatedDateIsAfter(LocalDate.now().minusDays(7));
//        List<Game> competition = gamesRepository.findByCreatedDateIsAfter(LocalDate.now().minusDays(7));
        Context context = new Context();
        context.setVariable("news", news);
        context.setVariable("quiz", quiz);
        //context.setVariable("competition", competition);
        userRepository.findByNewsletterIsTrue().forEach(user -> {
            mailService.sendMail("newsletter", user.getEmail(), context);
        });
    }

}
