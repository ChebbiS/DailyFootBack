package com.dailyfoot.repositories;

import com.dailyfoot.entities.Agenda;
import com.dailyfoot.entities.OwnerType;
import com.dailyfoot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    Optional<Agenda> findByOwnerType(OwnerType ownerType);
    List<Agenda> findByOwnerId(int ownerId);
    Optional<Agenda> findByOwnerIdAndOwnerType(int ownerId, User.Role ownerType);
    Optional<Agenda> findFirstByOwnerId(int ownerId);


}
