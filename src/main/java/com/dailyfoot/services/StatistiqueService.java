package com.dailyfoot.services;

import com.dailyfoot.dto.StatistiqueDTO;
import com.dailyfoot.entities.Statistique;
import com.dailyfoot.repositories.StatistiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class StatistiqueService {

    private final StatistiqueRepository statistiqueRepository;

    @Autowired
    public StatistiqueService(StatistiqueRepository statistiqueRepository) {
        this.statistiqueRepository = statistiqueRepository;
    }

    public List<StatistiqueDTO> getAllStatistiques() {
        return statistiqueRepository.findAll()
        .stream()
        .map(statistics -> new StatistiqueDTO(statistics))
        .toList();
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

    public Optional<Statistique> getStatistiqueByPlayerId(Integer PlayerId) {
        return statistiqueRepository.findByPlayerId(PlayerId);
    }

    public StatistiqueDTO updateFields(int id, Map<String, Object> updates) {
        Statistique stat = statistiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistiques inexistante")); // A capter dans les exceptions

        Map<String, Consumer<Object>> updatesMap = Map.of(
                "matchsJoues", value -> stat.setMatchsJoues((Integer) value),
                "buts", value -> stat.setButs((Integer) value),
                "passesDecisives", value -> stat.setPassesDecisives((Integer) value),
                "taille", value -> stat.setTaille(Double.parseDouble(value.toString())),
                "poids", value -> stat.setPoids(Double.parseDouble(value.toString())),
                "cartonsJaunes", value -> stat.setCartonsJaunes((Integer) value),
                "cartonsRouges", value -> stat.setCartonsRouges((Integer) value)
        );

        updates.forEach((key, value) -> {
            if (updatesMap.containsKey(key)) {
                updatesMap.get(key).accept(value);
            } //TO DO : refacto ça en plus simple ligne 44 à 67
            // ctrl + MAJ + F
        });

        statistiqueRepository.save(stat);
        return new StatistiqueDTO(stat);
    }
    public Optional<StatistiqueDTO> getStatsByPlayerId(Integer playerId) {
        return statistiqueRepository.findByPlayerId(playerId)
                .map(StatistiqueDTO::new);
    }
}