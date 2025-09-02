package com.dailyfoot.services;

import com.dailyfoot.entities.Agent;
import com.dailyfoot.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {
    private final AgentRepository agentRepository;


    @Autowired
    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Agent saveAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public Optional<Agent> getAgentById(Integer id) {
        return agentRepository.findById(id);
    }

    public void deleteAgent(Integer id) {
        agentRepository.deleteById(id);
    }

}
