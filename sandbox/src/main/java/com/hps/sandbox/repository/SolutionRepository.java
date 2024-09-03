package com.hps.sandbox.repository;

import com.hps.sandbox.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution,Integer> {
    List<Solution> findByProductRelease_ProductReleaseId(Integer id);
}
