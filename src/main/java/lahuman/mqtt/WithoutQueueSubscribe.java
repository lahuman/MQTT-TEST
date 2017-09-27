package lahuman.mqtt;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class WithoutQueueSubscribe implements MqttCallback {

	public void connectionLost(Throwable cause) {

		System.out.println(cause);
		System.exit(1);
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// delivery OK
//		if (token.isComplete()) {
//			if(token.getClient().isConnected())
//				 try {
//					System.out.println(
//					 token.getClient().getClientId() + "/" +
//					 new String(token.getMessage().getPayload()));
//				} catch (MqttException e) {
//					e.printStackTrace();
//				}
//		}

	}

	public Map<String, Integer> reciverCount = new HashMap<>();
	

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" + time + "  Topic:\t" + topic + "  Message:\t" + new String(message.getPayload())
				+ "  QoS:\t" + message.getQos());

	}

}
