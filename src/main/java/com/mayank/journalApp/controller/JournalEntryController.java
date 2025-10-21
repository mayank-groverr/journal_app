package com.mayank.journalApp.controller;


import com.mayank.journalApp.entity.JournalEntry;
import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.service.JournalEntryService;
import com.mayank.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


//Controller -> special type of classes / components for handling http request
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> entriesOfUser = user.getJournalEntries();
        if (entriesOfUser != null && !entriesOfUser.isEmpty()) {
            return new ResponseEntity<>(entriesOfUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@PathVariable String userName, @RequestBody JournalEntry myEntry) {
        try {
            User user = userService.findByUserName(userName);
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }


    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("id/{myId}/{userName}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
        journalEntryService.deleteEntryById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }


    @PutMapping("id/{Id}/{userName}")
    public ResponseEntity<JournalEntry> updateEntryById(
            @PathVariable ObjectId Id,
            @RequestBody JournalEntry updatedEntry,
            @PathVariable String userName
    ) {

        JournalEntry old = journalEntryService.getEntryById(Id).orElse(null);
        if (old != null) {
            old.setTitle(!updatedEntry.getTitle().isEmpty() ? updatedEntry.getTitle() : old.getTitle());
            old.setContent(updatedEntry.getContent() != null && !(updatedEntry.getContent().isEmpty()) ? updatedEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
