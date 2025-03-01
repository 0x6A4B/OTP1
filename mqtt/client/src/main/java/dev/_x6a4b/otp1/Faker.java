package dev._x6a4b.otp1;

import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.util.UUID;

public class Faker implements Hook<String, MqttMessage>{
    private Mqtt mqtt;
    private Sensors sensors;
    private final String topicBase = "sensor/data/";

    public Faker(){
        sensors = new Sensors();
        mqtt = new Mqtt(this,
                "tcp://otp1.0x6a4b.dev:1883",
                "FakerClient",
                "sensor/data/#",
                "test_user",
                "test1234"
        );
    }

    public void loop(){
        while(true){
            System.out.println("Sending log event to MQTT...");
            sensors.getUuids().forEach(uuid ->{
                try {
                    System.out.println("UUID: " + uuid);
                    mqtt.sendMessage(randomPayload("temperature"), "sensor/data/" + uuid);
                } catch (MqttException e) {
                    System.err.println("Error in MQTT msg send: " + e.getMessage());
                }
            });
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String randomPayload(String key){
        Double temp = Math.random() * 100;
        if(temp >= 40)
            temp = halver(temp);

        temp = (int)(temp*100)/100.0;
        return key + ";" + temp;
    }

    private double halver(double d){
        if(d >= 40)
            return halver(d/2);
        return d;
    }

    @Override
    public void handle(String topic, MqttMessage message) {
        System.out.println("HOOK!");
        // A new device added for pseudo sensor aka log faker
        if(topic.equals(topicBase + "add_device")) {
            String uuid = new String(message.getPayload());
            System.out.println("Received a new device: " + uuid);
            try {
                UUID.fromString(uuid);
                System.out.println("Adding new device to faker: " + uuid);
                sensors.addSensor(uuid);
            }catch (IllegalArgumentException e){
                System.err.println("UUID faulty!");
            }
        }
    }
}
