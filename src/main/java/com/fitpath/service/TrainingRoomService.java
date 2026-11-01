package com.fitpath.service;

import com.fitpath.model.TrainingRoom;
import com.fitpath.repository.TrainingRoomRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingRoomService {
    public static final String NOT_FOUND = "The training room with that id does not exist";

    @Autowired
    private TrainingRoomRepository repo;

    public String save(TrainingRoom room) {
        room.setId(null);
        repo.save(room);
        return "Training room saved successfully";
    }

    public List<TrainingRoom> getAll() { return repo.findAll(); }

    public Optional<TrainingRoom> getById(UUID id) { return repo.findById(id); }

    public String update(UUID id, TrainingRoom room) {
        Optional<TrainingRoom> opt = repo.findById(id);
        if (opt.isEmpty()) return NOT_FOUND;
        TrainingRoom existing = opt.get();
        existing.setRoomNumber(room.getRoomNumber());
        existing.setName(room.getName());
        repo.save(existing);
        return "Training room updated successfully";
    }

    public String delete(UUID id) {
        if (!repo.existsById(id)) return NOT_FOUND;
        repo.deleteById(id);
        return "Training room deleted successfully";
    }

    // C2
    public List<TrainingRoom> getFree() { return repo.findFreeRooms(); }
}
