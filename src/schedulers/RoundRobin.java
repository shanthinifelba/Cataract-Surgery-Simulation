/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulers;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author behnish
 */
public class RoundRobin {

    int noOfSurgons;
    int totalDays;
    int workingDaysPerWeek;
    int operatingRooms;
    int[] count;
    int[] weekCount;

    ArrayList<Integer> workingDaysTotal = new ArrayList<>();
    LinkedHashMap<Integer, LinkedHashMap<Integer, String>> schedule = new LinkedHashMap<>();

    public RoundRobin(int noOfSurgons, int totalDays, int workingDaysPerWeek, int operatingRooms) {
        count = new int[noOfSurgons];
        weekCount = new int[noOfSurgons];

        this.noOfSurgons = noOfSurgons;
        this.totalDays = totalDays;
        this.workingDaysPerWeek = workingDaysPerWeek;
        this.operatingRooms = operatingRooms;

        loadWorkingDays();
        init();

        resetCounter();
        weekCounter();

        execute();
    }

    private void loadWorkingDays() {
        int day = 1, counter = 0;
        while (day <= totalDays) {
            if (counter == 5) {
                day += 2;
                counter = 0;
            }
            if (day <= totalDays) {
                workingDaysTotal.add(day);
                day++;
                counter++;
            }
        }
    }

    private void init() {
        for (int i = 1; i <= noOfSurgons; i++) {
            schedule.put(i, new LinkedHashMap<Integer, String>());
            for (Integer integer : workingDaysTotal) {
                schedule.get(i).put(integer, "CON");
            }
        }
    }

    private void resetCounter() {
        for (int i = 0; i < noOfSurgons; i++) {
            count[i] = workingDaysPerWeek;
        }
    }

    private void weekCounter() {
        for (int i = 0; i < noOfSurgons; i++) {
            weekCount[i] = 0;
        }
    }

    private void execute() {
        int tCount = 0;
        for (Integer day : workingDaysTotal) {
            if (tCount == 5) {
                tCount = 0;
                weekCounter();
            }
            for (int i = 1; i <= operatingRooms; i++) {
                int id = returnNxtSurgonID();
                if (id != 0) {
                    schedule.get(id).put(day, "OP," + i);
                }
            }
            tCount++;
        }
    }

    private int returnNxtSurgonID() {
        for (int k = workingDaysPerWeek; k >= 1; k--) {
            for (int i = 0; i < count.length; i++) {
                if (weekCount[i] != workingDaysPerWeek) {
                    if (count[i] == k) {
                        count[i]--;
                        weekCount[i]++;
                        return i + 1;
                    }
                }
            }
        }
        resetCounter();
        for (int i = 0; i < count.length; i++) {
            if (weekCount[i] != workingDaysPerWeek) {
                if (count[i] == workingDaysPerWeek) {
                    count[i]--;
                    weekCount[i]++;
                    return i + 1;
                }
            }
        }

        return 0;
    }

    public LinkedHashMap<Integer, LinkedHashMap<Integer, String>> returnScheduler() {
        System.out.println(schedule.entrySet());
        return schedule;
    }
}
