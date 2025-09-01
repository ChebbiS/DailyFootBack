package com.dailyfoot.services;

import com.dailyfoot.dto.StatistiqueDTO;
import com.dailyfoot.entities.Statistique;
import com.dailyfoot.repositories.StatistiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public Optional<Statistique> getStatistiqueByPlayerId(Integer PlayerId) {
        return statistiqueRepository.findByPlayerId(PlayerId);
    }

        public StatistiqueDTO updateFields(int id, Map<String, Object> updates) {
            Statistique stat = statistiqueRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Statistiques inexistante")); // A capter dans les exceptions

            if(updates.containsKey("matchsJoues"))
                stat.setMatchsJoues((Integer) updates.get("matchsJoues"));

            if(updates.containsKey("buts"))
                stat.setButs((Integer) updates.get("buts"));

            if(updates.containsKey("passesDecisives"))
                stat.setPassesDecisives((Integer) updates.get("passesDecisives"));

            if(updates.containsKey("taille"))
                stat.setTaille(Double.parseDouble(updates.get("taille").toString()));

            if(updates.containsKey("poids"))
                stat.setPoids(Double.parseDouble(updates.get("poids").toString()));

            if(updates.containsKey("cartonsJaunes"))
                stat.setCartonsJaunes((Integer) updates.get("cartonsJaunes"));

            if(updates.containsKey("cartonsRouges"))
                stat.setCartonsRouges((Integer) updates.get("cartonsRouges"));

            statistiqueRepository.save(stat);

            return new StatistiqueDTO(stat);
        }
    }

