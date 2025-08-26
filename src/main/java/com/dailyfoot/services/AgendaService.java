package com.dailyfoot.services;

import com.dailyfoot.entities.Agenda;
import com.dailyfoot.entities.OwnerType;
import com.dailyfoot.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {
    private final AgendaRepository agendaRepository;

    @Autowired
    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }
    public List<Agenda> getAllAgendas() {
        return agendaRepository.findAll();
    }
    public Agenda saveAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }
    public Optional<Agenda> getAgendaById(Integer id) {
        return agendaRepository.findById(id);
    }
    public void deleteAgenda(Integer id) {
        agendaRepository.deleteById(id);
    }
    public Optional<Agenda> getAgendaByOwnerType(OwnerType ownerType) {
        return agendaRepository.findByOwnerType(ownerType);
    }
}
