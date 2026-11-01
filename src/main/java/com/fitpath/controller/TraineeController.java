package com.fitpath.controller;

import com.fitpath.model.Trainee;
import com.fitpath.service.CoachService;
import com.fitpath.service.TraineeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainees")
public class TraineeController {
    @Autowired
    private TraineeService service;
    @Autowired
    private CoachService coachService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Trainee trainee) {
        String msg = service.save(trainee);
        if (msg.equals(TraineeService.DUPLICATE_CODE)) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trainee>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    // B1
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Trainee>> byLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(service.getByLastName(lastName), HttpStatus.OK);
    }

    // C4
    @GetMapping("/of-coach/{coachId}")
    public ResponseEntity<Object> ofCoach(@PathVariable UUID coachId) {
        if (coachService.getById(coachId).isEmpty()) {
            return new ResponseEntity<>(CoachService.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getOfCoach(coachId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Trainee> opt = service.getById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(TraineeService.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Trainee trainee) {
        String msg = service.update(id, trainee);
        if (msg.equals(TraineeService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.equals(TraineeService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
