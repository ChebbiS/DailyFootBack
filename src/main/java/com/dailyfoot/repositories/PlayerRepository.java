package com.dailyfoot.repositories;

import com.dailyfoot.dto.PlayerResponse;
import com.dailyfoot.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Optional<Player> findByAccessCode(int accessCode);
}
