package com.fitpath.service;

import com.fitpath.model.Trainee;
import com.fitpath.repository.TraineeRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeService {
    public static final String NOT_FOUND = "The trainee with that id does not exist";
    public static final String DUPLICATE_CODE = "A trainee with that member code already exists";

    @Autowired
    private TraineeRepository repo;

    public String save(Trainee trainee) {
        if (repo.existsByMemberCode(trainee.getMemberCode())) return DUPLICATE_CODE;
        trainee.setId(null);
        repo.save(trainee);
        return "Trainee saved successfully";
    }

    public List<Trainee> getAll() { return repo.findAll(); }

    public Optional<Trainee> getById(UUID id) { return repo.findById(id); }

    public String update(UUID id, Trainee trainee) {
        Optional<Trainee> opt = repo.findById(id);
        if (opt.isEmpty()) return NOT_FOUND;
        Trainee existing = opt.get();
        existing.setFirstName(trainee.getFirstName());
        existing.setLastName(trainee.getLastName());
        existing.setMemberCode(trainee.getMemberCode());
        existing.setEmail(trainee.getEmail());
        repo.save(existing);
        return "Trainee updated successfully";
    }

    public String delete(UUID id) {
        if (!repo.existsById(id)) return NOT_FOUND;
        repo.deleteById(id);
        return "Trainee deleted successfully";
    }

    // B1
    public List<Trainee> getByLastName(String lastName) {
        return repo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4
    public List<Trainee> getOfCoach(UUID coachId) { return repo.findTraineesOfCoach(coachId); }
}
