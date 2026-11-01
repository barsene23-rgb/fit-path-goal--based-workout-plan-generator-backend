package com.fitpath.controller;

import com.fitpath.model.TrainingRoom;
import com.fitpath.service.TrainingRoomService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/training-rooms")
public class TrainingRoomController {
    @Autowired
    private TrainingRoomService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody TrainingRoom room) {
        return new ResponseEntity<>(service.save(room), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrainingRoom>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<List<TrainingRoom>> getFree() {
        return new ResponseEntity<>(service.getFree(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<TrainingRoom> opt = service.getById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(TrainingRoomService.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody TrainingRoom room) {
        String msg = service.update(id, room);
        if (msg.equals(TrainingRoomService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.equals(TrainingRoomService.NOT_FOUND)) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
