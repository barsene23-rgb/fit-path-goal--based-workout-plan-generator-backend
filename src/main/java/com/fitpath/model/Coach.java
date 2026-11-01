package com.fitpath.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "training_room_id", nullable = true)
    private TrainingRoom trainingRoom;

    @ManyToMany
    @JoinTable(name = "coach_fitness_goal",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "fitness_goal_id"))
    private List<FitnessGoal> fitnessGoals = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public TrainingRoom getTrainingRoom() { return trainingRoom; }
    public void setTrainingRoom(TrainingRoom trainingRoom) { this.trainingRoom = trainingRoom; }
    public List<FitnessGoal> getFitnessGoals() { return fitnessGoals; }
    public void setFitnessGoals(List<FitnessGoal> fitnessGoals) { this.fitnessGoals = fitnessGoals; }
}
