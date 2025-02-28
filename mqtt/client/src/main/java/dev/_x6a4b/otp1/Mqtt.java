package dev._x6a4b.otp1;


import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;



public class Mqtt {

    private String mqttBrokerUri;

    private String brokerPort;


    private String broker;
    private final String clientId = "API-Client2";

    private MqttClient client;


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
        System.out.println("received");
        System.out.println("MQTT message arrived: " + "\nTopic: " + topic
                + "\nQOS: " + message.getQos()
                + "\nContent: " + new String(message.getPayload()));

        System.out.println(new String(message.getPayload()));

        String uuid = topic.substring(topic.lastIndexOf("/") + 1, topic.length()-1);
        String payload = new String(message.getPayload());
        System.out.println("UUID: " + uuid);
    }

    private void handleDisconnected(MqttDisconnectResponse mqttDisconnectResponse){
        System.err.println("MQTT disconnected: " + mqttDisconnectResponse.getReasonString());
    }

    public void sendMessage(String messageText, String topic){
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
