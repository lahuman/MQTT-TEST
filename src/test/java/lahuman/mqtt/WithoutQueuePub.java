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
public class WithoutQueuePub {

	public static Queue<String> myQue = new LinkedList<String>();
	public static MqttClient client= null;
	public static boolean isFinish = false;
	public static final int QOS_LEVEL = 1;
	
	public static void main(String[] args) throws Exception {
		StopWatch stopWatch = new StopWatch();
		String id = MqttClient.generateClientId();
		System.out.println(id);
		client = new MqttClient("tcp://test.mosquitto.org:1883", id);
//		client = new MqttClient("tcp://20.20.20.32:1883", id);
		client.setCallback(new WithoutQueueSubscribe());
		client.connect();
		stopWatch.start();
		
		Stream.iterate(0, i->i+1)
			.limit(1000)
			.forEach(i->{
				
				MqttMessage message = new MqttMessage();
				message.setQos(QOS_LEVEL);
				message.setPayload(("Hello world from Java-"+i).getBytes());
				try {
					client.publish("/topic/withoutQueue", message);
				} catch (MqttException e) {
					e.printStackTrace();
				}		
			});

		
		stopWatch.stop();
		client.disconnect();
		System.out.println(stopWatch.toString());
	}
	
	
}
