package com.fitpath.repository;

import com.fitpath.model.Trainee;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TraineeRepository extends JpaRepository<Trainee, UUID> {

    // B1 - DERIVED: IgnoreCase + OrderBy
    List<Trainee> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    boolean existsByMemberCode(String memberCode);

    // C4 - JPQL, DISTINCT + path navigation
    @Query("SELECT DISTINCT w.trainee FROM WorkoutPlan w WHERE w.coach.id = :coachId")
    List<Trainee> findTraineesOfCoach(@Param("coachId") UUID coachId);
}
