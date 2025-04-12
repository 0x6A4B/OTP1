package controller;

import java.io.IOException;

public class MyController extends AbstractController {

    public void showLogSingUP() {
        try {
            gui.setScene("LogSingUp", 300, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDeviceList() {
        try {
            gui.setScene("DevicesList", 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDevice() {
        try {
            gui.setScene("Device", 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTest() {
        try {
            gui.setScene("Test", 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}