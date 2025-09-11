package com.dailyfoot.repositories;

import com.dailyfoot.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
    Optional<Statistic> findByPlayerId(int playerId);
    Optional<Statistic> findById(int Id);
}
