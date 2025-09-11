package com.dailyfoot.repositories;

import com.dailyfoot.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findByAgentId(int agentId);
    Optional<Player> findByEmail(String email);
    boolean existsByEmail(String email);
}
