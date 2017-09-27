package lahuman.mqtt;

import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class WithOueueSubscribe implements MqttCallback {

	public void connectionLost(Throwable cause) {

		System.out.println(cause);
		System.exit(1);
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// delivery OK
		if (token.isComplete()) {
			// System.out.println(
			// token.getClient().getClientId() + "/" +
			// new String(token.getMessage().getPayload()));
			if (null != WithQueuePub.myQue.peek()) {
				MqttMessage message = new MqttMessage();
				message.setQos(WithQueuePub.QOS_LEVEL);
				message.setPayload(WithQueuePub.myQue.poll().getBytes());
				try {
					token.getClient().publish("/topic/withQueue", message);
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("isFinish");
				WithQueuePub.isFinish = true;
			}

		}

	}

	public int val = -1;

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" + time + "  Topic:\t" + topic + "  Message:\t" + new String(message.getPayload())
				+ "  QoS:\t" + message.getQos());

	}

}
