package lahuman.mqtt;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		final String MQTT_BROKER_IP = "tcp://20.20.20.32:1883";
//		final String MQTT_BROKER_IP = "tcp://test.mosquitto.org:1883";

		try {
			MqttClient client = new MqttClient(MQTT_BROKER_IP, // URI
					MqttClient.generateClientId(), // ClientId
					new MemoryPersistence());

			client.connect();
			client.setCallback(new MqttCallback() {
				StopWatch stopWatch = new StopWatch();
				Map<String, Integer> counter = initMap();

				final int TOTAL_GROUP = 10;				
				final String SEND_MSG_COUNT = "10";
				boolean isStart = false;
				int hundred = 0;
				int totalCount = 0;

				public void connectionLost(Throwable cause) { // Called when the client lost the connection to the
																// broker
				}

				public void deliveryComplete(IMqttDeliveryToken arg0) {

				}

				public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
					try {
						if(!isStart) {
							isStart = true;
							stopWatch.start();
							System.out.println("Starting.....");
						}
						String[] data = arg1.toString().split("-");
//						System.out.println(arg0 + ": " + arg1.toString() + "| data[1]="+data[1]  + " | data[2]="+data[2]);
						counter.put(data[1], (counter.get(data[1]))+1);
						if( SEND_MSG_COUNT.equals(data[2].trim())) {
//							System.out.println(hundred);
							if(++hundred == TOTAL_GROUP ) {
								System.out.println("Ending.....");
								stopWatch.stop();
								printResult();
								reset();
							}
						}	
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				}

				
				private void printResult() {
					System.out.println("------------------------------------------");
					System.out.println("RESULT TIME : " + stopWatch.toString());
					IntStream.iterate(1, i -> i + 1)
					.limit(TOTAL_GROUP)
					.forEach(i->totalCount += counter.get(String.valueOf(i)));
					
					System.out.println("RESULT Count : "+totalCount );
					System.out.println("------------------------------------------");
				}

				private HashMap<String, Integer> initMap() {
					return new HashMap<String, Integer>() {{
						IntStream.iterate(1, i -> i + 1)
						.limit(TOTAL_GROUP)
						.forEach(i->put(String.valueOf(i), 0));
					}};
				}
				
				private void reset() {
					isStart = false;
					hundred = 0;
					stopWatch.reset();
					counter = initMap();
					totalCount= 0;
				}
			});

			client.subscribe("/topic/test", 0);
		}

		catch (MqttException e) {
			e.printStackTrace();
		} // Persistence
	}

}
