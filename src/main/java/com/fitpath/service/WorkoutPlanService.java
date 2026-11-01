package com.fitpath.service;

import com.fitpath.model.PlanStatus;
import com.fitpath.model.WorkoutPlan;
import com.fitpath.repository.SessionLogRepository;
import com.fitpath.repository.WorkoutPlanRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkoutPlanService {
    public static final String NOT_FOUND = "The workout plan with that id does not exist";
    public static final String PAST_DATE = "Workout plan date cannot be in the past";
    public static final String COACH_BUSY = "Coach already has a plan starting on that date";
    public static final String CANNOT_CHANGE = "Cannot change status from ";
    public static final String STATUS_UPDATED = "Workout plan status updated successfully";
    public static final String HAS_LOGS = "That workout plan has session logs, delete them first";

    @Autowired
    private WorkoutPlanRepository repo;
    @Autowired
    private SessionLogRepository sessionLogRepo;

    // A2 + D1
    public String save(WorkoutPlan plan) {
        if (plan.getStartDate().isBefore(LocalDate.now())) return PAST_DATE;
        if (plan.getCoach() != null && repo.existsByCoachIdAndStartDateAndStatusNot(
                plan.getCoach().getId(), plan.getStartDate(), PlanStatus.ABANDONED)) {
            return COACH_BUSY;
        }
        plan.setId(null);
        plan.setStatus(PlanStatus.GENERATED);   // always GENERATED, whatever the JSON says
        repo.save(plan);
        return "Workout plan saved successfully";
    }

    public List<WorkoutPlan> getAll() { return repo.findAll(); }

    public Optional<WorkoutPlan> getById(UUID id) { return repo.findById(id); }

    // The status is deliberately NOT copied here: it can only change through updateStatus
    public String update(UUID id, WorkoutPlan plan) {
        Optional<WorkoutPlan> opt = repo.findById(id);
        if (opt.isEmpty()) return NOT_FOUND;
        WorkoutPlan existing = opt.get();
        existing.setStartDate(plan.getStartDate());
        existing.setDaysPerWeek(plan.getDaysPerWeek());
        if (plan.getTrainee() != null) existing.setTrainee(plan.getTrainee());
        if (plan.getCoach() != null) existing.setCoach(plan.getCoach());
        repo.save(existing);
        return "Workout plan updated successfully";
    }

    // A2 + E3
    public String delete(UUID id) {
        Optional<WorkoutPlan> opt = repo.findById(id);
        if (opt.isEmpty()) return NOT_FOUND;
        if (sessionLogRepo.existsByWorkoutPlan(opt.get())) return HAS_LOGS;
        repo.deleteById(id);
        return "Workout plan deleted successfully";
    }

    // B2
    public List<WorkoutPlan> getByStatus(PlanStatus status) {
        return repo.findByStatusOrderByStartDateAsc(status);
    }

    // B3
    public List<WorkoutPlan> getBetween(LocalDate start, LocalDate end) {
        return repo.findByStartDateBetweenOrderByStartDateAsc(start, end);
    }

    // D2
    public String updateStatus(UUID id, PlanStatus newStatus) {
        Optional<WorkoutPlan> opt = repo.findById(id);
        if (opt.isEmpty()) return NOT_FOUND;
        WorkoutPlan plan = opt.get();
        PlanStatus current = plan.getStatus();

        boolean allowed = false;
        if (current == PlanStatus.GENERATED) {
            allowed = newStatus == PlanStatus.REVIEWED || newStatus == PlanStatus.ABANDONED;
        } else if (current == PlanStatus.REVIEWED) {
            allowed = newStatus == PlanStatus.ACTIVE || newStatus == PlanStatus.ABANDONED;
        }
        if (!allowed) return CANNOT_CHANGE + current + " to " + newStatus;

        plan.setStatus(newStatus);
        repo.save(plan);
        return STATUS_UPDATED;
    }

    // F1
    public List<Object[]> statsByStatus() { return repo.countPerStatus(); }

    // F2
    @Transactional
    public String cancelDay(UUID coachId, LocalDate date) {
        int count = repo.cancelDay(coachId, date, PlanStatus.ABANDONED, PlanStatus.ACTIVE);
        return count + " workout plans cancelled";
    }

    // Bonus 2
    public Page<WorkoutPlan> getPage(Pageable pageable) { return repo.findAll(pageable); }
}
