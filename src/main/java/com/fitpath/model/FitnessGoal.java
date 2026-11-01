package com.fitpath.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fitness_goal")
public class FitnessGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToMany(mappedBy = "fitnessGoals")
    @JsonIgnore
    private List<Coach> coaches;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Coach> getCoaches() { return coaches; }
    public void setCoaches(List<Coach> coaches) { this.coaches = coaches; }
}
