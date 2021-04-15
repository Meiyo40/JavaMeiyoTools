package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Plan;

import java.util.ArrayList;
import java.util.List;

class RaidToolBox {
    static List<Plan> orderPlanByRaidTier(List<Plan> rawList){
        List<Plan> list = new ArrayList<Plan>();
        List<List<Plan>> tierList = raidListByTier(rawList);
        if(tierList.get(0).size() > 0) {
            list.addAll(tierList.get(0));
        }
        if(tierList.get(1).size() > 0) {
            list.addAll(tierList.get(1));
        }
        if(tierList.get(2).size() > 0) {
            list.addAll(tierList.get(2));
        }
        if(tierList.get(3).size() > 0) {
            list.addAll(tierList.get(3));
        }
        if(tierList.get(4).size() > 0) {
            list.addAll(tierList.get(4));
        }

        return list;
    }

    static List<List<Plan>> raidListByTier(List<Plan> plans) {
        List<List<Plan>> list = new ArrayList<>();
        List<Plan> t4 = new ArrayList<Plan>();
        List<Plan> t5 = new ArrayList<Plan>();
        List<Plan> t6 = new ArrayList<Plan>();
        List<Plan> t65 = new ArrayList<Plan>();
        List<Plan> unmatchted = new ArrayList<Plan>();

        for (Plan el: plans) {
            if(el.getRaidName().equals("karazhan") || el.getRaidName().equals("gruul_mag")) {
                t4.add(el);
            } else if (el.getRaidName().equals("ssc") || el.getRaidName().equals("tempestKeep")) {
                t5.add(el);
            } else if(el.getRaidName().equals("hyjal") || el.getRaidName().equals("blackTemple")) {
                t6.add(el);
            } else if(el.getRaidName().equals("sunwell")) {
                t65.add(el);
            } else {
                unmatchted.add(el);
            }
        }

        list.add(t4);
        list.add(t5);
        list.add(t6);
        list.add(t65);
        list.add(unmatchted);

        return list;
    }
}