package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Plan;
import com.meiyotools.main.model.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        Optional<List<Plan>> list = this.repository.findAllByRaidNameOrderByPriorityDesc(raidName);
        return list.isPresent() ? list.get() : null;
    }

    public Plan setPlan(Plan plan) {
        plan.setCreatedAt(LocalDate.now());
        plan.setVersion(plan.getVersion() + 1);
        return this.repository.save(plan);
    }

    public void deletePlan(Long id) {
        this.repository.deleteById(id);
    }

    public List<Plan> getAllPlans() {
        Optional<List<Plan>> optionalList = this.repository.findAllByOrderByPriorityDesc();
        List<Plan> list;

        if(optionalList.isPresent()) {
            list = RaidToolBox.orderPlanByRaidTier(optionalList.get());
        } else {
            return null;
        }
        return list;
    }

    public Plan getPlanById(Long planId) {
        Optional<Plan> plan = this.repository.findById(planId);
        return plan.isPresent() ? plan.get() : null;
    }

    public List<List<Plan>> getAllPlansOrdered() {
        Optional<List<Plan>> optionalList = this.repository.findAllByOrderByPriorityDesc();
        List<List<Plan>> list;

        if(optionalList.isPresent()) {
            list = RaidToolBox.raidListByTier(optionalList.get());
        } else {
            return null;
        }
        return list;
    }

    public Plan updatePriority(Long id, int priority) {
        Optional<Plan> old = this.repository.findById(id);
        if(old.isPresent()) {
            System.out.println(old.get().toString());
            old.get().setPriority(priority);
            System.out.println(old.get().toString());
            return this.repository.save(old.get());
        } else {
            return null;
        }
    }
}
