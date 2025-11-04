package com.mayank.journalApp.scheduler;

import com.mayank.journalApp.entity.JournalEntry;
import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.enums.Sentiment;
import com.mayank.journalApp.repository.UserRepositoryImpl;
import com.mayank.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepository;

    @Scheduled(cron = "0 0 9 * * SUN")
    public  void fetchUserAndSendSaMail(){
        List<User> users = userRepository.getUserForSentimentalAnalysis();
        for(User user: users){
            List<Sentiment> sentiments = user.getJournalEntries().stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(JournalEntry::getSentiment).toList();
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment != null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0) +1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
            }

        }
    }


}
