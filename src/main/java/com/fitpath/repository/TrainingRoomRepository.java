package com.fitpath.repository;

import com.fitpath.model.TrainingRoom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainingRoomRepository extends JpaRepository<TrainingRoom, UUID> {

    // D4 - DERIVED
    Optional<TrainingRoom> findByRoomNumber(int roomNumber);

    // C2 - JPQL, NOT EXISTS
    @Query("SELECT l FROM TrainingRoom l WHERE NOT EXISTS "
            + "(SELECT p FROM Coach p WHERE p.trainingRoom = l) ORDER BY l.roomNumber")
    List<TrainingRoom> findFreeRooms();
}
