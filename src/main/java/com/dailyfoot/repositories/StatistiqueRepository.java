package com.dailyfoot.repositories;

import com.dailyfoot.entities.Statistique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatistiqueRepository extends JpaRepository<Statistique, Integer> {
    Optional<Statistique> findByPlayerId(int playerId);
    Optional<Statistique> findById(int Id);
}
