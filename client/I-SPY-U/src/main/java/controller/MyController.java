package controller;

import java.io.IOException;

import view.GUI;

public class MyController {

    public void showLogSingUP() {
        try {
            GUI.setScene("LogSingUp", 300, 450);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDeviceList() {
        try {
            GUI.setScene("DevicesList", 500, 550);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDevice() {
        try {
            GUI.setScene("Device", 500, 550);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}