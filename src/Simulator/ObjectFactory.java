/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author behnish
 */
public class ObjectFactory {

    private static int simulationLenth;
    private static MainFrame mainFrame;
    private static Parameter parmeters;
    private static Animation animation;
    private static Performance performance;

    public static double twoDaySkip = 1200;
    public static double operationTime = 25;
    public static double recoverTime = 15;
    public static int noOfPatients = 15;
    public static int currentDay = 1;

    private static Simulation simulation;
    private static double animationClock = 1.0;

    private static Map<Integer, LinkedHashMap<Integer, Patient>> patientG = new LinkedHashMap<>();
    private static Map<Integer, TraceObject> trace = new LinkedHashMap<>();
    private static ConcurrentHashMap<Integer, TraceObject> drawingList = new ConcurrentHashMap<>();

    private static LinkedHashMap<Integer, LinkedHashMap<Integer, String>> surgeonSchedule = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, Integer> workingDays = new LinkedHashMap<>();
    
    private static LinkedHashMap<Integer, Patient> backlogPatients = new LinkedHashMap<>();

    private static ArrayList<Integer> operationDays = new ArrayList<>();
    
    private static LinkedHashMap<Integer, Integer> limit = new LinkedHashMap<>();

    public static LinkedHashMap<Integer, Patient> getBacklogPatients() {
        return backlogPatients;
    }

    public static void setBacklogPatients(LinkedHashMap<Integer, Patient> backlogPatients) {
        ObjectFactory.backlogPatients = backlogPatients;
    }
    
    public static LinkedHashMap<Integer, Integer> getLimit() {
        return limit;
    }    

    public static ArrayList<Integer> getOperationDays() {
        return operationDays;
    }

    public static LinkedHashMap<Integer, Integer> getWorkingDays() {
        return workingDays;
    }

    public static void setWorkingDays(LinkedHashMap<Integer, Integer> workingDays) {
        ObjectFactory.workingDays = workingDays;
    }

    public static LinkedHashMap<Integer, LinkedHashMap<Integer, String>> getSurgeonSchedule() {
        return surgeonSchedule;
    }

    public static void setSurgeonSchedule(LinkedHashMap<Integer, LinkedHashMap<Integer, String>> surgeonSchedule) {
        ObjectFactory.surgeonSchedule = surgeonSchedule;
    }

    public static Map<Integer, LinkedHashMap<Integer, Patient>> getPatientG() {
        return patientG;
    }

    public static void setPatientG(Map<Integer, LinkedHashMap<Integer, Patient>> patientG) {
        ObjectFactory.patientG = patientG;
    }

    public static Map<Integer, TraceObject> getDrawingList() {
        return drawingList;
    }

    public static void setDrawingList(ConcurrentHashMap<Integer, TraceObject> drawingList) {
        ObjectFactory.drawingList = drawingList;
    }

    public static double getAnimationClock() {
        return animationClock;
    }

    public static void setAnimationClock(double animationClock) {
        ObjectFactory.animationClock = animationClock;
    }

    public static double getTwoDaySkip() {
        return twoDaySkip;
    }

    public static void setTwoDaySkip(double twoDaySkip) {
        ObjectFactory.twoDaySkip = twoDaySkip;
    }

    public static double getOperationTime() {
        return operationTime;
    }

    public static void setOperationTime(double operationTime) {
        ObjectFactory.operationTime = operationTime;
    }

    public static double getRecoverTime() {
        return recoverTime;
    }

    public static void setRecoverTime(double recoverTime) {
        ObjectFactory.recoverTime = recoverTime;
    }

    public static int getNoOfPatients() {
        return noOfPatients;
    }

    public static void setNoOfPatients(int noOfPatients) {
        ObjectFactory.noOfPatients = noOfPatients;
    }

    public static Simulation getSimulation() {
        return simulation;
    }

    public static void setSimulation(Simulation simulation) {
        ObjectFactory.simulation = simulation;
    }

    public static Map<Integer, TraceObject> getTrace() {
        return trace;
    }

    public static void setTrace(Map<Integer, TraceObject> trace) {
        ObjectFactory.trace = trace;
    }

    public static Parameter getParmeters() {
        return parmeters;
    }

    public static void setParmeters(Parameter parmeters) {
        ObjectFactory.parmeters = parmeters;
    }

    public static Animation getAnimation() {
        return animation;
    }

    public static void setAnimation(Animation animation) {
        ObjectFactory.animation = animation;
    }

    public static Performance getPerformance() {
        return performance;
    }

    public static void setPerformance(Performance performance) {
        ObjectFactory.performance = performance;
    }

    public static int getSimulationLenth() {
        return simulationLenth;
    }

    public static void setSimulationLenth(int simulationLenth) {
        ObjectFactory.simulationLenth = simulationLenth;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        ObjectFactory.mainFrame = mainFrame;
    }

    public synchronized static void updateClock(double clock) {
        if (ObjectFactory.getAnimationClock() < clock) {            
            ObjectFactory.setAnimationClock(clock);
        }
    }
}
