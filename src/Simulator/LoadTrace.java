/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 *
 * @author behnish
 */
public class LoadTrace {

    String currentTraceFile;
    private LinkedHashMap<Integer, TraceObject> localTrace = new LinkedHashMap<>();
    private LinkedHashMap<Integer, ArrayList> operationUtilization = new LinkedHashMap<>();
    private LinkedHashMap<Integer, ArrayList> surgeonUtilization = new LinkedHashMap<>();

    private LinkedHashMap<Integer, Integer> surgeonQueueSize = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Integer> operationQueueSize = new LinkedHashMap<>();

    public LinkedHashMap<Integer, Integer> getSurgeonQueue() {
        return surgeonQueueSize;
    }

    public LinkedHashMap<Integer, Integer> getOperationQueue() {
        return operationQueueSize;
    }

    public LinkedHashMap<Integer, TraceObject> getLocalTrace() {
        return localTrace;
    }

    public LinkedHashMap<Integer, ArrayList> getOperationUtilization() {
        return operationUtilization;
    }

    public LinkedHashMap<Integer, ArrayList> getSurgeonUtilization() {
        return surgeonUtilization;
    }

    public LoadTrace(String currentTraceFile) {
        ObjectFactory.getTrace().clear();
        this.currentTraceFile = currentTraceFile;
    }

    public void loadTraceIntoDS() throws FileNotFoundException, IOException {
        int i = 1;
        File file = new File(currentTraceFile);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null) {
                    line = line.replace("<", "");
                    line = line.replace(">", "");

                    String[] raw = line.split(",");

                    TraceObject t = new TraceObject();
                    t.setType(raw[0]);
                    t.setCount(i);
                    t.setId(Integer.parseInt(raw[1]));
                    t.setPatientId(Integer.parseInt(raw[2]));
                    t.setSimulationClock(Double.parseDouble(raw[3]));

                    if (raw[0].equals("RZ_ENT")) {
                        t.setWaitTime1(Double.parseDouble(raw[4]));
                        t.setWaitTime2(Double.parseDouble(raw[5]));
                        t.setSurgeonId(Integer.parseInt(raw[6]));
                        t.setOperationSurgeonId(Integer.parseInt(raw[7]));
                        localTrace.put(i, t);
                    }
                    switch (raw[0]) {
                        case "OP_START":
                            if (operationUtilization.get(t.getId()) == null) {
                                ArrayList arr = new ArrayList();
                                arr.add(t.getPatientId());
                                operationUtilization.put(t.id, arr);
                            } else {
                                operationUtilization.get(t.id).add(t.getPatientId());
                            }
                            break;
                        case "OP_END":
                            if (surgeonUtilization.get(Integer.parseInt(raw[4])) == null) {
                                ArrayList arr = new ArrayList();
                                arr.add(t.getPatientId());
                                surgeonUtilization.put(Integer.parseInt(raw[4]), arr);
                            } else {
                                surgeonUtilization.get(Integer.parseInt(raw[4])).add(t.getPatientId());
                            }
                            break;
                        case "SQ_SIZE":
                            surgeonQueueSize.put(t.getId(), t.getPatientId());
                            break;
                        case "OPT_SIZE":
                            operationQueueSize.put(t.getId(), t.getPatientId());
                            break;
                    }

                    ObjectFactory.getTrace().put(i, t);

                    i++;
                }
            }
        }
    }
}
