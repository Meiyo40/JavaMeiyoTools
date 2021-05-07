package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Plan;

import java.util.ArrayList;
import java.util.List;

class RaidToolBox {

    /**
     * Take a list of plans and ordered the content by tier priority index 0 :: T4 > T5 > T6 > T65 > N
     * @param rawList
     * @return Return an ordered list of the plans T4 > T5 > T6 > T65 > N
     */
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

    /**
     * Build a list of list containing all plans, each sub list inside the main list is a game tier (T4/5/6/65/N)
     * @param plans
     * @return Return a list of list of plans, the list contains all plans, each sublist is a tier (T4/5/6/65/N)
     */
    static List<List<Plan>> raidListByTier(List<Plan> plans) {
        List<List<Plan>> list = new ArrayList<>();
        List<Plan> t4 = new ArrayList<Plan>();
        List<Plan> t5 = new ArrayList<Plan>();
        List<Plan> t6 = new ArrayList<Plan>();
        List<Plan> t65 = new ArrayList<Plan>();
        List<Plan> za = new ArrayList<Plan>();
        List<Plan> unmatchted = new ArrayList<Plan>();

        for (Plan el: plans) {
            if(el.getRaidName().equals("karazhan") || el.getRaidName().equals("gruul_mag")) {
                el.setRaidName( el.getRaidName().equals("karazhan") ? "KZ" : "OD" );
                t4.add(el);
            } else if (el.getRaidName().equals("karazhan2")) {
                el.setRaidName("KZ2");
                t4.add(el);
            } else if (el.getRaidName().equals("ssc") || el.getRaidName().equals("tempestKeep")) {
                el.setRaidName( el.getRaidName().equals("ssc") ? "SSC" : "TK" );
                t5.add(el);
            } else if(el.getRaidName().equals("hyjal") || el.getRaidName().equals("blackTemple")) {
                el.setRaidName( el.getRaidName().equals("hyjal") ? "Hyjal" : "BT" );
                t6.add(el);
            } else if(el.getRaidName().equals("sunwell")) {
                el.setRaidName("SW");
                t65.add(el);
            } else if(el.getRaidName().equals("zulaman") || el.getRaidName().equals("zulaman2")) {
                el.setRaidName( el.getRaidName().equals("zulaman") ? "ZA" : "ZA2" );
                za.add(el);
            } else {
                unmatchted.add(el);
            }
        }

        list.add(t4);
        list.add(t5);
        list.add(t6);
        list.add(t65);
        list.add(za);
        list.add(unmatchted);

        return list;
    }
}
