package com.fitpath.repository;

import com.fitpath.model.FitnessGoal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FitnessGoalRepository extends JpaRepository<FitnessGoal, UUID> {

    // C3 - JPQL, IS EMPTY (no COUNT)
    @Query("SELECT g FROM FitnessGoal g WHERE g.coaches IS EMPTY")
    List<FitnessGoal> findUnused();
}
