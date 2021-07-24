package com.example.springjpa.Repositories;

import com.example.springjpa.Entitites.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CycleRepository extends JpaRepository<Cycle, Long>
{
    Optional<Cycle> findByCycleNo(long cycleNo);
    List findByCycleStand(String cycleStand);
}
