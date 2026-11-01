package com.fitpath.controller;

import com.fitpath.model.Coach;
import com.fitpath.service.CoachService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {
    @Autowired
    private CoachService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Coach coach) {
        return new ResponseEntity<>(service.save(coach), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Coach>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    // C1
    @GetMapping("/by-fitness-goal")
    public ResponseEntity<List<Coach>> byFitnessGoal(@RequestParam String name) {
        return new ResponseEntity<>(service.getByFitnessGoal(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Coach> opt = service.getById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(CoachService.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Coach coach) {
        String msg = service.update(id, coach);
        if (msg.equals(CoachService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.equals(CoachService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // D3
    @PutMapping("/{coachId}/fitness-goals/{fitnessGoalId}")
    public ResponseEntity<String> addFitnessGoal(@PathVariable UUID coachId, @PathVariable UUID fitnessGoalId) {
        String msg = service.addFitnessGoal(coachId, fitnessGoalId);
        if (msg.equals(CoachService.NOT_FOUND) || msg.equals(CoachService.GOAL_NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals(CoachService.GOAL_ALREADY)) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // D4
    @PatchMapping("/{coachId}/training-room")
    public ResponseEntity<String> changeRoom(@PathVariable UUID coachId, @RequestParam int roomNumber) {
        String msg = service.changeTrainingRoom(coachId, roomNumber);
        if (msg.equals(CoachService.NOT_FOUND) || msg.equals(CoachService.ROOM_NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals(CoachService.ROOM_TAKEN)) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
