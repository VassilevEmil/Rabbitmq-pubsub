import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Subscriber {

    private final static String queue_name = "canteen_products";

    public static void main(String[] args) throws IOException, TimeoutException {

        //factory pattern for connection

        ConnectionFactory factory = new ConnectionFactory();
        //set the host name
        factory.setHost("localhost");

        //create connection and subscribe the channel

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(queue_name, false, false, false, null);

        //use the default consumer to control the deliveries

        Consumer consumer = new DefaultConsumer(channel) {
            @Override public void handleDelivery(String consumerTag, Envelope envelope,
                                                 AMQP.BasicProperties properties, byte[] body) throws  IOException{
                //super.handleDelivery(consumerTag, envelop, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println("[Sub] Received '" + message + "'");
            }
        };

        //consume using: queue, bool noAck, callback
        channel.basicConsume(queue_name, true, consumer);

    }
}
