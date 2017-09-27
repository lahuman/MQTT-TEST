package lahuman.mqtt;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Unit test for simple App.
 */
public class WithQueuePub {

	public static Queue<String> myQue = new LinkedList<String>();
	public static MqttClient client= null;
	public static boolean isFinish = false;
	public static final int QOS_LEVEL = 2;
	
	public static void main(String[] args) throws Exception {
		StopWatch stopWatch = new StopWatch();
		String id = MqttClient.generateClientId();
		System.out.println(id);
		client = new MqttClient("tcp://test.mosquitto.org:1883", id);
//		client = new MqttClient("tcp://20.20.20.32:1883", id);
		client.setCallback(new WithOueueSubscribe());
		client.connect();
		Stream.iterate(0, i->i+1)
			.limit(1000)
			.forEach(i->{
				myQue.offer(("Hello world from Java-"+i));
			});
		stopWatch.start();
		MqttMessage message = new MqttMessage();
		message.setQos(QOS_LEVEL);
		message.setPayload(myQue.poll().getBytes());
		try {
			client.publish("/topic/withQueue", message);
		} catch (MqttException e) {
			e.printStackTrace();
		}		
		while(finish()) {
			Thread.sleep(1);
		}
		stopWatch.stop();
//		client.disconnect();
		
		System.out.println(stopWatch.toString());
		
	}
	
	public static boolean finish() {
		try {
			if (isFinish) {
				client.disconnect();
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return !isFinish;
	}
}
