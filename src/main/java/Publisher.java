import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {

    private final static String quene_name = "canteen_products";

    public static void main(String[] args) throws Exception {

        String [] viaProducts = {"Cappuccino, caffe Latte, Pizza Hawai"};

        //factory pattern to init connection

        ConnectionFactory factory = new ConnectionFactory();

        //set the host name;

        factory.setHost("localhost");

        //create the connection using the factory instance
        //and init the channel using connection
        //channel is responsible for sending, receiving, plus some queue operations

        try
                (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()){

            //with channel, we can declare, bind, unbind, del queue,...
            //declare the queue
            //queue name, passive?, exclusive?, autoDel? any arguments

            channel.queueDeclare(quene_name, false, false, false, null);

            //publish message directly to the provided queue
            for (String vp: viaProducts){
                String message = "Canteens new product: " + vp;

                //exchange, routingKey, AMQP.properties, byte[] body of msg

                channel.basicPublish("", quene_name, null, message.getBytes());
                System.out.println("[Pub] Sent ' " + message + " '");
            }

        }

    }
}
