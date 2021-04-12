package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Plan;
import com.meiyotools.main.model.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    private final PlanRepository repository;

    @Autowired
    public PlanService (PlanRepository planRepository) {
        this.repository = planRepository;
    }

    public Plan getPlan(String planName) {
        return this.repository.findByPlanName(planName);
    }

    public List<Plan> getRaidPlans(String raidName) {
        return this.repository.findAllByRaidName(raidName);
    }

    public Plan setPlan(Plan plan) {
        return this.repository.save(plan);
    }

    public void deletePlan(Long id) {
        this.repository.deleteById(id);
    }

    public List<Plan> getAllPlans() {
        return this.repository.findAll();
    }

}
