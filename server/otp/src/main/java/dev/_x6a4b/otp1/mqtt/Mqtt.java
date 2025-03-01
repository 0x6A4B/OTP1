package dev._x6a4b.otp1.mqtt;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.LogEntry;
import dev._x6a4b.otp1.service.DeviceLogService;
import dev._x6a4b.otp1.service.DeviceService;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Mqtt {
    private static final Logger log = LoggerFactory.getLogger(Mqtt.class);
    @Value("${mqtt.broker-uri}")
    private String mqttBrokerUri;
    @Value("${mqtt.broker-port}")
    private String brokerPort;

    @Autowired
    private Environment env;

    private String broker;
    private final String clientId = "API-Client";

    private MqttClient client;

    @Autowired
    private DeviceLogService deviceLogService;
    @Autowired
    private DeviceService deviceService;

    public Mqtt(){

  //  public void start(){
        //broker = mqttBrokerUri + ":" + brokerPort;
        broker = "tcp://otp1.0x6a4b.dev:1883";
        //broker = env.getProperty("mqtt.broker-uri");
        System.out.println("Broker: " + broker);

        try {
            client = new MqttClient(broker, clientId);
            MqttConnectionOptions options = new MqttConnectionOptions();
            options.setUserName("test_user");
            options.setPassword("test1234".getBytes());
            client.connect(options);

            client.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                   handleMessage(s, mqttMessage);
                }

                @Override
                public void deliveryComplete(IMqttToken iMqttToken) {
                    try {
                        System.out.println("MQTT delivery complete: " + iMqttToken.getMessage().getPayload().toString());
                    } catch (MqttException e) {
                        System.err.println("MQTT deliverycomplete error: " + e.getMessage());
                    }
                }

                @Override
                public void connectComplete(boolean b, String s) {
                    System.out.println("MQTT connection complete: " + b + " - " + s);
                }

                @Override
                public void authPacketArrived(int i, MqttProperties mqttProperties) {

                }

                @Override
               public void disconnected(MqttDisconnectResponse mqttDisconnectResponse){
                    handleDisconnected(mqttDisconnectResponse);
               }

                @Override
                public void mqttErrorOccurred(MqttException e) {

                }
            });

            String topic = "sensor/data/#";
            int qos = 1;

            client.subscribe(topic, qos);

            //client.disconnect();
            //client.close();
        } catch (MqttException e) {
            System.err.println("Error in MQTT client: " + e.getMessage());
        }


    }

    private void handleMessage(String topic, MqttMessage message){
        System.out.println("MQTT message arrived: "
                + "\nTopic: " + topic
                + "\nQOS: " + message.getQos()
                + "\nContent: " + new String(message.getPayload()));

        String uuid = topic.substring(topic.lastIndexOf("/") + 1, topic.length());
        String[] payload = new String(message.getPayload()).split(";");

        LogEntry logEntry = new LogEntry();
        Device device = deviceService.getDeviceByUuid(UUID.fromString(uuid)).get();
        System.out.println("DEVICE: " + device.getName());

        logEntry.setDevice(device);
        logEntry.setLogkey(payload[0]);
        logEntry.setValue(payload[1]);
        System.out.println("LogEntry: " + logEntry.getDevice().getName() + " - " + logEntry.getLogkey() + " - " + logEntry.getValue());
        System.out.println(deviceLogService.saveLogEntry(logEntry).getDevice().getName());

    }
    private void handleDisconnected(MqttDisconnectResponse mqttDisconnectResponse){
        System.err.println("MQTT disconnected: " + mqttDisconnectResponse.getReasonString());
    }

    private void sendMessage(String messageText, String topic){
        //String topic = "topic/test";
        int qos = 1;
        //String msg = "Hello MQTT";
        MqttMessage message = new MqttMessage(messageText.getBytes());
        message.setQos(qos);
        try {
            client.publish(topic, message);
        } catch (MqttException e) {
            System.err.println("Error in MQTT msg send: " + e.getMessage());
        }
    }

    private void closeConnection(){
        try {
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            System.err.println("MQTT error in closing connection: " + e.getMessage());
        }
    }


}
