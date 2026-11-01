package com.fitpath.repository;

import com.fitpath.model.SessionLog;
import com.fitpath.model.WorkoutPlan;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionLogRepository extends JpaRepository<SessionLog, UUID> {

    // E2 - JPQL with path navigation
    @Query("SELECT d FROM SessionLog d WHERE d.workoutPlan.trainee.memberCode = :memberCode "
            + "ORDER BY d.loggedDate DESC")
    List<SessionLog> findByTraineeMemberCode(@Param("memberCode") String memberCode);

    // E3 - DERIVED
    boolean existsByWorkoutPlan(WorkoutPlan workoutPlan);
}
