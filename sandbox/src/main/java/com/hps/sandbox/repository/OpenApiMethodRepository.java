package com.hps.sandbox.repository;

import com.hps.sandbox.entity.OpenApiMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenApiMethodRepository extends JpaRepository<OpenApiMethod, Integer> {
}
