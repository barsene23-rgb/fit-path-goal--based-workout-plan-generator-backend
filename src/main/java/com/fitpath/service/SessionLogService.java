package com.fitpath.service;

import com.fitpath.model.PlanStatus;
import com.fitpath.model.SessionLog;
import com.fitpath.model.WorkoutPlan;
import com.fitpath.repository.SessionLogRepository;
import com.fitpath.repository.WorkoutPlanRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionLogService {
    public static final String PLAN_NOT_FOUND = "The workout plan with that id does not exist";
    public static final String NOT_ACTIVE = "Session logs are only for ACTIVE workout plans";
    public static final String NOT_FOUND = "The session log with that id does not exist";

    @Autowired
    private SessionLogRepository repo;
    @Autowired
    private WorkoutPlanRepository planRepo;

    // E1
    public String save(UUID workoutPlanId, SessionLog log) {
        Optional<WorkoutPlan> opt = planRepo.findById(workoutPlanId);
        if (opt.isEmpty()) return PLAN_NOT_FOUND;
        WorkoutPlan plan = opt.get();
        if (plan.getStatus() != PlanStatus.ACTIVE) return NOT_ACTIVE;

        log.setId(null);
        log.setLoggedDate(LocalDate.now());
        log.setWorkoutPlan(plan);
        repo.save(log);
        return "Session log saved successfully";
    }

    public List<SessionLog> getAll() { return repo.findAll(); }

    public Optional<SessionLog> getById(UUID id) { return repo.findById(id); }

    // E2
    public List<SessionLog> getByTrainee(String memberCode) {
        return repo.findByTraineeMemberCode(memberCode);
    }

    // E3
    public String delete(UUID id) {
        if (!repo.existsById(id)) return NOT_FOUND;
        repo.deleteById(id);
        return "Session log deleted successfully";
    }
}
