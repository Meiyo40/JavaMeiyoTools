package com.meiyotools.main.model.repository;

import com.meiyotools.main.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByPlanName(String planName);
    Optional<Plan> findByRaidNameAndPlanName(String raidName, String planName);
    Optional<List<Plan>> findAllByRaidNameOrderByPriorityDesc(String raidName);
    Optional<List<Plan>> findAllByOrderByPriorityDesc();
}