package com.fitpath.controller;

import com.fitpath.model.PlanStatus;
import com.fitpath.model.WorkoutPlan;
import com.fitpath.service.WorkoutPlanService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {
    @Autowired
    private WorkoutPlanService service;

    // A2 + D1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody WorkoutPlan plan) {
        String msg = service.save(plan);
        if (msg.equals(WorkoutPlanService.PAST_DATE)) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        } else if (msg.equals(WorkoutPlanService.COACH_BUSY)) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkoutPlan>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    // B2
    @GetMapping("/by-status")
    public ResponseEntity<List<WorkoutPlan>> byStatus(@RequestParam PlanStatus status) {
        return new ResponseEntity<>(service.getByStatus(status), HttpStatus.OK);
    }

    // B3
    @GetMapping("/between")
    public ResponseEntity<List<WorkoutPlan>> between(@RequestParam String start, @RequestParam String end) {
        LocalDate from = LocalDate.parse(start);
        LocalDate to = LocalDate.parse(end);
        return new ResponseEntity<>(service.getBetween(from, to), HttpStatus.OK);
    }

    // F1
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> statsByStatus() {
        return new ResponseEntity<>(service.statsByStatus(), HttpStatus.OK);
    }

    // Bonus 2:  /page?page=0&size=5&sort=startDate,desc
    @GetMapping("/page")
    public ResponseEntity<Page<WorkoutPlan>> page(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @RequestParam(defaultValue = "startDate,desc") String sort) {
        String[] parts = sort.split(",");
        Sort.Direction direction = parts.length > 1 ? Sort.Direction.fromString(parts[1]) : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, parts[0]));
        return new ResponseEntity<>(service.getPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<WorkoutPlan> opt = service.getById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(WorkoutPlanService.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody WorkoutPlan plan) {
        String msg = service.update(id, plan);
        if (msg.equals(WorkoutPlanService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // A2 + E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.equals(WorkoutPlanService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.equals(WorkoutPlanService.HAS_LOGS)) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam PlanStatus status) {
        String msg = service.updateStatus(id, status);
        if (msg.equals(WorkoutPlanService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else if (msg.startsWith(WorkoutPlanService.CANNOT_CHANGE)) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // F2
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID coachId, @RequestParam String date) {
        LocalDate day = LocalDate.parse(date);
        return new ResponseEntity<>(service.cancelDay(coachId, day), HttpStatus.OK);
    }
}
