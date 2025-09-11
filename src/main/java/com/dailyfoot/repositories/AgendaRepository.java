package com.dailyfoot.repositories;

import com.dailyfoot.entities.Agenda;
import com.dailyfoot.entities.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    Optional<Agenda> findByOwnerType(OwnerType ownerType);
    Optional<Agenda> findByOwnerId(int ownerId);
}
