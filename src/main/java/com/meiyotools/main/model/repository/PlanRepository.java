package com.meiyotools.main.model.repository;

import com.meiyotools.main.model.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan findByPlanName(String planName);
    List<Plan> findAllByRaidName(String raidName);
}
