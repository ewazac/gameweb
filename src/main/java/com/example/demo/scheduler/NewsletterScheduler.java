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
/**Class for sending newsletter where time is scheduled*/
public class NewsletterScheduler {

    private final MailService mailService;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final QuizRepository quizRepository;

    //@Scheduled(cron = "0 0 7 ? * MON")
//    @Scheduled(cron = "0 0,5,10,20 10 ? * MON")
    //@Scheduled(cron = "0 4,10,14,20 11,12,13 ? * TUE")
    //@Scheduled(fixedRate = 10000)
    public void sendNewsletter() {
        List<News> news = newsRepository.findByCreatedDateIsAfterAndSendMailIsTrue(LocalDate.now().minusDays(7));
        List<Quiz> quiz = quizRepository.findByCreatedDateIsAfterAndSendMailIsTrue(LocalDate.now().minusDays(7));
        Context context = new Context();
        context.setVariable("news", news);
        context.setVariable("quiz", quiz);
        userRepository.findByNewsletterIsTrue().forEach(user -> {
            mailService.sendMail("newsletter", user.getEmail(), context);
        });
    }

}
