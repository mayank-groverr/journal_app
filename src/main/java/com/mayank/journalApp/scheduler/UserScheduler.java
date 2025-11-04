package com.mayank.journalApp.scheduler;

import com.mayank.journalApp.entity.JournalEntry;
import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.repository.UserRepositoryImpl;
import com.mayank.journalApp.service.EmailService;
import com.mayank.journalApp.service.SentimentAnalysisServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisServices sentimentAnalysisServices;

    @Scheduled(cron = "0 0 9 * * SUN")
    public  void fetchUserAndSendSaMail(){
        List<User> users = userRepository.getUserForSentimentalAnalysis();
        for(User user: users){
            List<String> filteredEntries = user.getJournalEntries().stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(JournalEntry::getContent).toList();
            String entry = String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisServices.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }
    }


}
