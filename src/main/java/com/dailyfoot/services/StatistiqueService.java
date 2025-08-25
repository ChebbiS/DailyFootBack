package com.dailyfoot.services;

import com.dailyfoot.entities.Statistique;
import com.dailyfoot.repositories.StatistiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatistiqueService {

    private final StatistiqueRepository statistiqueRepository;

    @Autowired
    public StatistiqueService(StatistiqueRepository statistiqueRepository) {
        this.statistiqueRepository = statistiqueRepository;
    }

    public List<Statistique> getAllStatistiques() {
        return statistiqueRepository.findAll();
    }

    public Optional<Statistique> getStatistiqueById(Integer id) {
        return statistiqueRepository.findById(id);
    }

    public Statistique saveStatistique(Statistique statistique) {
        return statistiqueRepository.save(statistique);
    }

    public void deleteStatistique(Integer id) {
        statistiqueRepository.deleteById(id);
    }

    public Optional<Statistique> getStatistiqueByPlayerId(Integer id) {
        return statistiqueRepository.findAll()
                .stream()
                .filter(statistique -> statistique.getPlayer().getPlayerId() == id)
                .findFirst();
    }
}
