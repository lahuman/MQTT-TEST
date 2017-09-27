package lahuman.mqtt;

import java.util.stream.Stream;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AppSubTest {

	public static void main(String[] args) throws Exception {
		final MqttClient client = new MqttClient("tcp://test.mosquitto.org:1883", MqttClient.generateClientId());
//		final MqttClient client = new MqttClient("tcp://20.20.20.32:1883", MqttClient.generateClientId());
		client.setCallback(new WithOueueSubscribe());
		client.connect();
		client.subscribe("/topic/test", WithoutQueuePub.QOS_LEVEL);
	}
}
