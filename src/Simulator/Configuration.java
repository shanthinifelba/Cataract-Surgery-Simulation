/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simulator;

/**
 *
 * @author IDontKnow
 */
public class Configuration {
    
    public static int surgeonConsultingTime = 30;
    public static int operatingTime = 30;
    public static int postOperationRecoveryTime = 20;
    
    public static int oneDaySimulationClockUnits = 600;
    public static int delayBetweenPatientInADay = 50; // mean using poossion distribution
    
    public static int totalNoOfPatientSurgeonSees = 10;
    public static int operationPerSurgeon = 5;
    public static int decisionTime = 1200;
    
    public static boolean global = false;
    public static int speed = 500;
    public static int costOfPatientWaiting = 93;
    public static int minimumSurgeryTime = 4;
    public static int acceptedSurgeryTime = 15;
    
}
