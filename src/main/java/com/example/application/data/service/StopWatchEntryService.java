package com.example.application.data.service;
import com.example.application.*;
import com.example.application.data.entity.StopWatchEntry;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StopWatchEntryService {
    private static final Logger LOGGER = Logger.getLogger(StopWatchEntryService.class.getName());
    private StopWatchEntryServiceRepository stopWatchRepos;

    public StopWatchEntryService(StopWatchEntryServiceRepository entryRepository) {
        this.stopWatchRepos = entryRepository;
    }
    
     public List<StopWatchEntry> findAll() {
        return stopWatchRepos.findAll();
    }


    public List<StopWatchEntry> findAll(String stringFilter) {
        //if (stringFilter == null || stringFilter.isEmpty()) {
            return stopWatchRepos.findAll();
/*        } else {
            return stopWatchRepos.search(stringFilter);
        }
*/            
    }

    public long count() {
        return stopWatchRepos.count();
    }

    public void delete(StopWatchEntry entry) {
        stopWatchRepos.delete(entry);
    }

    public void save(StopWatchEntry entry) {
        if (entry == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        stopWatchRepos.save(entry);
    }
    @PostConstruct
    public void populateTestData() {

        //ExampleDataGenerator<StopWatchEntry> shipmentRepositoryGenerator = new ExampleDataGenerator<>(StopWatchEntry.class,
        //LocalDateTime.of(2021, 4, 19, 0, 0, 0));
        List<StopWatchEntry> entries = new ArrayList<>();
        // LocalDate dateOfTask, String message, String taskDescr, long sumOfMinutes, DlName dlName, Worktype workType
entries.add(new StopWatchEntry(LocalDate.now(), "message1", StopWatchEntry.DlName.Monica, StopWatchEntry.Worktype.WSUPP, "10"));
entries.add(new StopWatchEntry(LocalDate.now(), "message2", StopWatchEntry.DlName.DiesUndDas, StopWatchEntry.Worktype.WREAL, "23"));
entries.add(new StopWatchEntry(LocalDate.now(), "message3", StopWatchEntry.DlName.QPortal, StopWatchEntry.Worktype.WOTHER, "130"));
/*        entries.add(new StopWatchEntry(LocalDate.now(),"MSG-1",StopWatchEntry.DlName.Monica, StopWatchEntry.Worktype.REAL, 12));
        entries.add(new StopWatchEntry(LocalDate.now(), 1, StopWatchEntry.DlName.QPortal, StopWatchEntry.Worktype.MEET));
        entries.add(new StopWatchEntry(LocalDate.now(), 33, StopWatchEntry.DlName.SRMAcceptance, StopWatchEntry.Worktype.SUPP));
        */
        if (stopWatchRepos.count() == 0) {
            stopWatchRepos.saveAll(entries);
        }
                    
                    /*
                    Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
                            .map(StopWatchEntry::new)
                            .collect(Collectors.toList())
                    );
        }
        
                    /*
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(
                    Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
                            .map(Company::new)
                            .collect(Collectors.toList()));
        }
*/
/*
        if (stopWatchRepos.count() == 0) {
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            stopWatchRepos.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                            "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                            "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                            "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                            "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                            "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                            "Jaydan Jackson", "Bernard Nilsen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Contact contact = new Contact();
                                contact.setFirstName(split[0]);
                                contact.setLastName(split[1]);
                                contact.setCompany(companies.get(r.nextInt(companies.size())));
                                contact.setStatus(Contact.Status.values()[r.nextInt(Contact.Status.values().length)]);
                                String email = (contact.getFirstName() + "." + contact.getLastName() + "@" + contact.getCompany().getName().replaceAll("[\\s-]", "") + ".com").toLowerCase();
                                contact.setEmail(email);
                                return contact;
                            }).collect(Collectors.toList()));
        }
    }
*/
    //                        return null;
    }

    public void saveAll(List<StopWatchEntry> entries) {
          
        stopWatchRepos.saveAll(entries);
    }
}