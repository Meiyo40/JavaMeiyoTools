package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.Plan;
import com.meiyotools.main.service.PlanService;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class PlanController {
    private final PlanService service;
    private final UserService userService;

    @Autowired
    public PlanController(PlanService planService, UserService userService) {
        this.service = planService;
        this.userService = userService;
    }

    @GetMapping("/plans")
    public ResponseEntity<List<Plan>> getAllPlans() {
        return new ResponseEntity<>(this.service.getAllPlans(), HttpStatus.OK);
    }

    @GetMapping("/plan/{planName}")
    public ResponseEntity<Plan> getPlan(@PathVariable String planName) {
        return new ResponseEntity<>(this.service.getPlan(planName), HttpStatus.OK);
    }

    @GetMapping("/raid/{raidName}")
    public ResponseEntity<List<Plan>> getRaidPlans(@PathVariable String raidName) {
        return new ResponseEntity<>(this.service.getRaidPlans(raidName), HttpStatus.OK);
    }

    @PostMapping("/plan")
    public ResponseEntity<Plan> setPlan(@RequestBody Plan plan, HttpServletRequest request) {
        if(userService.isLogged(request)) {
            return new ResponseEntity<>(this.service.setPlan(plan), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(plan, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/plan")
    public ResponseEntity<Plan> updatePlan(@RequestBody Plan plan, HttpServletRequest request) {
        if(userService.isLogged(request)) {
            return new ResponseEntity<>(this.service.setPlan(plan), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(plan, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<Long> deletePlan(@PathVariable Long id, HttpServletRequest request) {
        if(userService.isLogged(request)) {
            this.service.deletePlan(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id, HttpStatus.FORBIDDEN);
        }
    }
}
