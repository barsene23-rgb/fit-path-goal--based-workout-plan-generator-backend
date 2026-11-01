package com.fitpath.controller;

import com.fitpath.model.SessionLog;
import com.fitpath.service.SessionLogService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session-logs")
public class SessionLogController {
    @Autowired
    private SessionLogService service;

    // E1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID workoutPlanId, @RequestBody SessionLog log) {
        String msg = service.save(workoutPlanId, log);
        if (msg.equals(SessionLogService.PLAN_NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals(SessionLogService.NOT_ACTIVE)) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionLog>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    // E2
    @GetMapping("/trainee/{memberCode}")
    public ResponseEntity<List<SessionLog>> byTrainee(@PathVariable String memberCode) {
        return new ResponseEntity<>(service.getByTrainee(memberCode), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<SessionLog> opt = service.getById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(SessionLogService.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.equals(SessionLogService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
