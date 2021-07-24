package com.example.springjpa.Repositories;

import com.example.springjpa.Entitites.Stands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StandsRepository extends JpaRepository<Stands, Long>
{
    Optional<Stands> findByName(String name);
}
