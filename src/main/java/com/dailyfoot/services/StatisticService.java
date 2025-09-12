package com.dailyfoot.services;

import com.dailyfoot.dto.StatisticDTO;
import com.dailyfoot.entities.Statistic;
import com.dailyfoot.repositories.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public List<StatisticDTO> getAllStatistics() {
        return statisticRepository.findAll()
                .stream()
                .map(statistics -> new StatisticDTO(statistics))
                .toList();
    }

    public Optional<Statistic> getStatisticById(Integer id) {
        return statisticRepository.findById(id);
    }

    public Statistic saveStatistic(Statistic statistic) {
        return statisticRepository.save(statistic);
    }

    public void deleteStatistic(Integer id) {
        statisticRepository.deleteById(id);
    }

    public Optional<Statistic> getStatisticByPlayerId(Integer PlayerId) {
        return statisticRepository.findByPlayerId(PlayerId);
    }

    public StatisticDTO updateFields(int id, Map<String, Object> updates) {
        Statistic stat = statisticRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statistiques inexistante")); // A capter dans les exceptions

        Map<String, Consumer<Object>> updatesMap = Map.of(
                "matchesPlayed", value -> stat.setMatchesPlayed((Integer) value),
                "goals", value -> stat.setGoals((Integer) value),
                "assists", value -> stat.setAssists((Integer) value),
                "height", value -> stat.setHeight(Double.parseDouble(value.toString())),
                "weight", value -> stat.setWeight(Double.parseDouble(value.toString())),
                "yellowCards", value -> stat.setYellowCards((Integer) value),
                "redCards", value -> stat.setRedCards((Integer) value)
        );

        updates.forEach((key, value) -> {
            if (updatesMap.containsKey(key)) {
                updatesMap.get(key).accept(value);
            } //TO DO : refacto ça en plus simple ligne 44 à 67
            // ctrl + MAJ + F
        });

        statisticRepository.save(stat);
        return new StatisticDTO(stat);
    }

    public Optional<StatisticDTO> getStatsByPlayerId(Integer playerId) {
        return statisticRepository.findByPlayerId(playerId)
                .map(StatisticDTO::new);
    }
}