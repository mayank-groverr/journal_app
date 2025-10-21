package com.mayank.journalApp.service;

import com.mayank.journalApp.entity.JournalEntry;
import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {

            User user = userService.findByUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            user.setUserName(null);
            userService.saveEntry(user);


    }

    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);

    }


    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> getEntryById(ObjectId id) {

        return journalEntryRepository.findById(id);

    }


    @Transactional
    public void deleteEntryById(ObjectId id, String userName) {

        User user = userService.findByUserName(userName);
        JournalEntry entry = getEntryById(id).get();
        journalEntryRepository.deleteById(id);
        user.getJournalEntries().remove(entry);
        userService.saveEntry(user);

    }


}


//controller ---> service ---> repository