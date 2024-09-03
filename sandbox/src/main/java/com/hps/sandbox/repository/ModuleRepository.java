package com.hps.sandbox.repository;

import com.hps.sandbox.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ModuleRepository extends JpaRepository<Module,Integer> {

    List<Module> findBySolution_SolutionId(Integer solutionId);

}


