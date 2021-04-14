package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Plan;
import com.meiyotools.main.model.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {
    private final PlanRepository repository;

    @Autowired
    public PlanService (PlanRepository planRepository) {
        this.repository = planRepository;
    }

    public Plan getPlan(String planName) {
        Optional<Plan> plan = this.repository.findByPlanName(planName);
        return plan.isPresent() ? plan.get() : null;
    }

    public Plan getPlan(String raidName, String planName) {
        Optional<Plan> plan = this.repository.findByRaidNameAndPlanName(raidName, planName);
        return plan.isPresent() ? plan.get() : null;
    }

    public List<Plan> getRaidPlans(String raidName) {
        Optional<List<Plan>> list = this.repository.findAllByRaidName(raidName);
        return list.isPresent() ? list.get() : null;
    }

    public Plan setPlan(Plan plan) {
        plan.setCreatedAt(LocalDate.now());
        return this.repository.save(plan);
    }

    public void deletePlan(Long id) {
        this.repository.deleteById(id);
    }

    public List<Plan> getAllPlans() {
        return this.repository.findAll();
    }

    public Plan getPlanById(Long planId) {
        Optional<Plan> plan = this.repository.findById(planId);
        return plan.isPresent() ? plan.get() : null;
    }
}
