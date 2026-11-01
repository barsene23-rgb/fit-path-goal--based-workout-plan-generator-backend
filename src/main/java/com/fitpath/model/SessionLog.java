package com.fitpath.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "session_log")
public class SessionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String exerciseName;
    private String notes;
    private int repetitions;
    private LocalDate loggedDate;

    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public int getRepetitions() { return repetitions; }
    public void setRepetitions(int repetitions) { this.repetitions = repetitions; }
    public LocalDate getLoggedDate() { return loggedDate; }
    public void setLoggedDate(LocalDate loggedDate) { this.loggedDate = loggedDate; }
    public WorkoutPlan getWorkoutPlan() { return workoutPlan; }
    public void setWorkoutPlan(WorkoutPlan workoutPlan) { this.workoutPlan = workoutPlan; }
}
