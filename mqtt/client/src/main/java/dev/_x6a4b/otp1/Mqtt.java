package dev._x6a4b.otp1;


import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;


public class Mqtt {
    private MqttClient client;
    private Hook hook;


    public Mqtt(Hook hook, String broker, String clientId, String topic, String username, String password){
        this.hook = hook;
        System.out.println("Broker: " + broker);

        try {
            client = new MqttClient(broker, clientId);
            MqttConnectionOptions options = new MqttConnectionOptions();
            options.setUserName(username);
            options.setPassword(password.getBytes());
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

        // Callback to calling classes handler
        hook.handle(topic, message);
    }

    private void handleDisconnected(MqttDisconnectResponse mqttDisconnectResponse){
        System.err.println("MQTT disconnected: " + mqttDisconnectResponse.getReasonString());
    }

    public void sendMessage(String messageText, String topic) throws MqttException{
        int qos = 1;
        MqttMessage message = new MqttMessage(messageText.getBytes());
        message.setQos(qos);
        client.publish(topic, message);

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
