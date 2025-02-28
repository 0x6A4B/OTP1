package dev._x6a4b.otp1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Mqtt mqtt = new Mqtt();

        String payload = "temperature;36.5";

        String uuid = "13318808-a7e6-4ae3-90fe-e2e2179d1f2d";

        for(int i = 0; i < 5; i++) {
            //mqtt.sendMessage(uuid + ";" + payload, "sensor/data");
            mqtt.sendMessage(payload, "sensor/data/" + uuid);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}