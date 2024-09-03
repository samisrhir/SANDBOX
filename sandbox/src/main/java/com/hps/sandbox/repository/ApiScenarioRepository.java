package com.hps.sandbox.repository;

import com.hps.sandbox.entity.ApiScenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiScenarioRepository extends JpaRepository<ApiScenario,Integer> {
}
