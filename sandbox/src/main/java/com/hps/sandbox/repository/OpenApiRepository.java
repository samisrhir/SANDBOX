package com.hps.sandbox.repository;

import com.hps.sandbox.entity.OpenAPiConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenApiRepository extends JpaRepository<OpenAPiConfig,Integer> {
}
