package com.fitpath.service;

import com.fitpath.model.PlanStatus;
import com.fitpath.repository.CoachRepository;
import com.fitpath.repository.SessionLogRepository;
import com.fitpath.repository.TraineeRepository;
import com.fitpath.repository.TrainingRoomRepository;
import com.fitpath.repository.WorkoutPlanRepository;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private TraineeRepository traineeRepo;
    @Autowired
    private CoachRepository coachRepo;
    @Autowired
    private TrainingRoomRepository roomRepo;
    @Autowired
    private WorkoutPlanRepository planRepo;
    @Autowired
    private SessionLogRepository logRepo;

    public Map<String, Long> summary() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("trainees", traineeRepo.count());
        map.put("coaches", coachRepo.count());
        map.put("freeRooms", (long) roomRepo.findFreeRooms().size());   // reuses C2
        map.put("plansToday", planRepo.countByStartDate(LocalDate.now()));
        map.put("generated", planRepo.countByStatus(PlanStatus.GENERATED));
        map.put("sessionLogs", logRepo.count());
        return map;
    }
}
