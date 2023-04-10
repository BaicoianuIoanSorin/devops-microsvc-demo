package dk.via.doc1.microservice.controller;

import dk.via.doc1.microservice.accessdata.EntryRepository;
import dk.via.doc1.microservice.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/entries/v1")
public class EntryController {

    private final EntryRepository entryRepository;

    @Autowired
    public EntryController(EntryRepository ep)
    {
        entryRepository = ep;
    }

    @GetMapping("/count")
    public Long getEntryCount() {
        return entryRepository.count();
    }

    @PostMapping("/entry")
    public Entry createEntry(@Validated @RequestBody Entry entry)
    {
        return entryRepository.save(entry);
    }

    @GetMapping("/entry/{id}")
    public Entry getNewestEntry(@PathVariable(value = "id") Long entryId) {
        Optional<Entry> entry = entryRepository.findById(entryId);
        return entry.orElseGet(Entry::new);
    }
}
