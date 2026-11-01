package com.fitpath.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "training_room")
public class TrainingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int roomNumber;
    private String name;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
