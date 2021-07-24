package com.example.springjpa.Repositories;

import com.example.springjpa.Entitites.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyRepository extends JpaRepository<Journey, Long>
{
    Optional<Journey> findByCycleId (long cycleId);
    Optional<Journey> findByUserId (long userId);
}
