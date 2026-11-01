package com.fitpath.service;

import com.fitpath.model.Coach;
import com.fitpath.model.FitnessGoal;
import com.fitpath.model.TrainingRoom;
import com.fitpath.repository.CoachRepository;
import com.fitpath.repository.FitnessGoalRepository;
import com.fitpath.repository.TrainingRoomRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoachService {
    public static final String NOT_FOUND = "The coach with that id does not exist";
    public static final String GOAL_NOT_FOUND = "That fitness goal does not exist";
    public static final String GOAL_ALREADY = "The coach already has that fitness goal";
    public static final String GOAL_ADDED = "Fitness goal added to the coach";
    public static final String ROOM_NOT_FOUND = "The training room with that roomNumber does not exist";
    public static final String ROOM_TAKEN = "That training room is taken by another coach";
    public static final String ROOM_CHANGED = "Training room changed successfully";

    @Autowired
    private CoachRepository repo;
    @Autowired
    private FitnessGoalRepository goalRepo;
    @Autowired
    private TrainingRoomRepository roomRepo;

    public String save(Coach coach) {
        coach.setId(null);
        repo.save(coach);
        return "Coach saved successfully";
    }

    public List<Coach> getAll() { return repo.findAll(); }

    public Optional<Coach> getById(UUID id) { return repo.findById(id); }

    public String update(UUID id, Coach coach) {
        Optional<Coach> opt = repo.findById(id);
        if (opt.isEmpty()) return NOT_FOUND;
        Coach existing = opt.get();
        existing.setFirstName(coach.getFirstName());
        existing.setLastName(coach.getLastName());
        existing.setPhone(coach.getPhone());
        existing.setTrainingRoom(coach.getTrainingRoom());
        if (coach.getFitnessGoals() != null && !coach.getFitnessGoals().isEmpty()) {
            existing.setFitnessGoals(coach.getFitnessGoals());
        }
        repo.save(existing);
        return "Coach updated successfully";
    }

    public String delete(UUID id) {
        if (!repo.existsById(id)) return NOT_FOUND;
        repo.deleteById(id);
        return "Coach deleted successfully";
    }

    // C1
    public List<Coach> getByFitnessGoal(String name) { return repo.findByFitnessGoalName(name); }

    // D3
    @Transactional
    public String addFitnessGoal(UUID coachId, UUID fitnessGoalId) {
        Optional<Coach> coachOpt = repo.findById(coachId);
        if (coachOpt.isEmpty()) return NOT_FOUND;
        Optional<FitnessGoal> goalOpt = goalRepo.findById(fitnessGoalId);
        if (goalOpt.isEmpty()) return GOAL_NOT_FOUND;

        Coach coach = coachOpt.get();
        FitnessGoal goal = goalOpt.get();
        if (coach.getFitnessGoals().contains(goal)) return GOAL_ALREADY;

        coach.getFitnessGoals().add(goal);
        repo.save(coach);               // Coach is the owning side
        return GOAL_ADDED;
    }

    // D4
    public String changeTrainingRoom(UUID coachId, int roomNumber) {
        Optional<Coach> coachOpt = repo.findById(coachId);
        if (coachOpt.isEmpty()) return NOT_FOUND;
        Optional<TrainingRoom> roomOpt = roomRepo.findByRoomNumber(roomNumber);
        if (roomOpt.isEmpty()) return ROOM_NOT_FOUND;

        Coach coach = coachOpt.get();
        TrainingRoom room = roomOpt.get();
        boolean alreadyInThatRoom = coach.getTrainingRoom() != null
                && coach.getTrainingRoom().getId().equals(room.getId());
        if (!alreadyInThatRoom && repo.existsByTrainingRoom(room)) return ROOM_TAKEN;

        coach.setTrainingRoom(room);    // old room is automatically free: no coach points to it anymore
        repo.save(coach);
        return ROOM_CHANGED;
    }
}
