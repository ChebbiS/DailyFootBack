package com.dailyfoot.repositories;

import com.dailyfoot.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByOwnerTypeAndOwnerId(Event.OwnerType ownerType, int ownerId);
    List<Event> findByAgendaId(int agendaId);

}
