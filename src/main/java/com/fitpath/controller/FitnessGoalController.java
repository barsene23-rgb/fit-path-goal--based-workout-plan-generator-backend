package com.fitpath.controller;

import com.fitpath.model.FitnessGoal;
import com.fitpath.service.FitnessGoalService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fitness-goals")
public class FitnessGoalController {
    @Autowired
    private FitnessGoalService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody FitnessGoal goal) {
        return new ResponseEntity<>(service.save(goal), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FitnessGoal>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<FitnessGoal>> getUnused() {
        return new ResponseEntity<>(service.getUnused(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<FitnessGoal> opt = service.getById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(FitnessGoalService.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody FitnessGoal goal) {
        String msg = service.update(id, goal);
        if (msg.equals(FitnessGoalService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.equals(FitnessGoalService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
