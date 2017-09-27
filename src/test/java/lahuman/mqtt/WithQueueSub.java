package lahuman.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class WithQueueSub {

	public static void main(String[] args) throws Exception {
		final MqttClient client = new MqttClient("tcp://test.mosquitto.org:1883", MqttClient.generateClientId());
//		final MqttClient client = new MqttClient("tcp://20.20.20.32:1883", MqttClient.generateClientId());
		client.setCallback(new WithOueueSubscribe());
		client.connect();
		client.subscribe("/topic/withQueue", WithQueuePub.QOS_LEVEL);
	}
}
