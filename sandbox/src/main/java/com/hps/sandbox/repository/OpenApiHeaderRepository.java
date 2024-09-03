package com.hps.sandbox.repository;

import com.hps.sandbox.entity.OpenApiHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenApiHeaderRepository extends JpaRepository<OpenApiHeader,Integer> {
}
