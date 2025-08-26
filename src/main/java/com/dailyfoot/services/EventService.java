package com.dailyfoot.services;

import com.dailyfoot.entities.Event;
import com.dailyfoot.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
        public List<Event> getAllEvents() {
            return eventRepository.findAll();
        }

        public Optional<Event> getEventById(Integer id) {
            return eventRepository.findById(id);
        }
        public Event saveEvent(Event event) {
            return eventRepository.save(event);
        }
        public void deleteEvent(Integer id) {
            eventRepository.deleteById(id);
        }

}
