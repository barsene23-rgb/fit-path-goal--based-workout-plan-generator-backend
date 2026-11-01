package com.fitpath.repository;

import com.fitpath.model.PlanStatus;
import com.fitpath.model.WorkoutPlan;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {

    // B2 - DERIVED: OrderBy
    List<WorkoutPlan> findByStatusOrderByStartDateAsc(PlanStatus status);

    // B3 - DERIVED: Between (inclusive)
    List<WorkoutPlan> findByStartDateBetweenOrderByStartDateAsc(LocalDate start, LocalDate end);

    // D1 - DERIVED: existsBy + And + Not
    boolean existsByCoachIdAndStartDateAndStatusNot(UUID coachId, LocalDate startDate, PlanStatus status);

    // F1 - JPQL: GROUP BY + COUNT
    @Query("SELECT w.status, COUNT(w) FROM WorkoutPlan w GROUP BY w.status")
    List<Object[]> countPerStatus();

    // F2 - JPQL: UPDATE (ACTIVE and already-ABANDONED plans are left alone)
    @Modifying
    @Query("UPDATE WorkoutPlan w SET w.status = :abandoned "
            + "WHERE w.coach.id = :coachId AND w.startDate = :date "
            + "AND w.status <> :active AND w.status <> :abandoned")
    int cancelDay(@Param("coachId") UUID coachId,
                  @Param("date") LocalDate date,
                  @Param("abandoned") PlanStatus abandoned,
                  @Param("active") PlanStatus active);

    // Bonus 1 - DERIVED countBy
    long countByStatus(PlanStatus status);
    long countByStartDate(LocalDate startDate);
}
