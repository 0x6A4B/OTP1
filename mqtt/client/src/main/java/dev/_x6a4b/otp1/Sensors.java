package dev._x6a4b.otp1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Sensors {
    private List<String> uuids = new ArrayList<>();
    private final String filename = "data/sensors.list";

    public Sensors(){
        loadSensors();
    }

    public void addSensor(String uuid){
        System.out.println("Added: " + uuid);
        uuids.add(uuid);
        saveSensors();
    }

    public List<String> getUuids(){
        return uuids;
    }

    private void loadSensors(){
        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String uuid;
            while((uuid = br.readLine()) != null)
                uuids.add(uuid);
            br.close();
            fr.close();
        } catch (Exception e) {
            System.err.println("Error loading: " + e.getMessage());
        }
    }

    private void saveSensor(String uuid){
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(uuid);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private void saveSensors(){
        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            uuids.forEach(uuid -> {
                try {
                    bw.write(uuid+"\n");
                } catch (IOException e) {
                    System.err.println("Error writing file: " + e.getMessage());
                }
            });
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.err.println("Error loading: " + e.getMessage());
        }
    }
}
