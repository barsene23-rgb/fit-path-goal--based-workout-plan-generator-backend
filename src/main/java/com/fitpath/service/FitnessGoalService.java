package com.fitpath.service;

import com.fitpath.model.FitnessGoal;
import com.fitpath.repository.FitnessGoalRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FitnessGoalService {
    public static final String NOT_FOUND = "The fitness goal with that id does not exist";

    @Autowired
    private FitnessGoalRepository repo;

    public String save(FitnessGoal goal) {
        goal.setId(null);
        repo.save(goal);
        return "Fitness goal saved successfully";
    }

    public List<FitnessGoal> getAll() { return repo.findAll(); }

    public Optional<FitnessGoal> getById(UUID id) { return repo.findById(id); }

    public String update(UUID id, FitnessGoal goal) {
        Optional<FitnessGoal> opt = repo.findById(id);
        if (opt.isEmpty()) return NOT_FOUND;
        FitnessGoal existing = opt.get();
        existing.setName(goal.getName());
        repo.save(existing);
        return "Fitness goal updated successfully";
    }

    public String delete(UUID id) {
        if (!repo.existsById(id)) return NOT_FOUND;
        repo.deleteById(id);
        return "Fitness goal deleted successfully";
    }

    // C3
    public List<FitnessGoal> getUnused() { return repo.findUnused(); }
}
