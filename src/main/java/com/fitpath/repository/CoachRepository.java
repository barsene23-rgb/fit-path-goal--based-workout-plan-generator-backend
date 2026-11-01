package com.fitpath.repository;

import com.fitpath.model.Coach;
import com.fitpath.model.TrainingRoom;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoachRepository extends JpaRepository<Coach, UUID> {

    // C1 - JPQL, JOIN on collection + LOWER
    @Query("SELECT DISTINCT c FROM Coach c JOIN c.fitnessGoals g WHERE LOWER(g.name) = LOWER(:name)")
    List<Coach> findByFitnessGoalName(@Param("name") String name);

    // D4 - DERIVED
    boolean existsByTrainingRoom(TrainingRoom trainingRoom);
}
