package com.hps.sandbox.repository;

import com.hps.sandbox.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario,Integer> {
}
