import com.rabbitmq.client.*;

public class Worker
{
	private final static String QUEUE_NAME = "task_queue";
	
	public static void main(String[] args) throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
				boolean durable = true;
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		
		System.out.println("Waiting for message, to exit presse CTRL + C");
		
		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws java.io.IOException{
				String message = new String(body, "UTF-8");
				
				System.out.println(" [x] Received '" + message + "'");
				
				try{
					doWork(message);
				}catch (InterruptedException ie){
					System.out.println("Interrupted Exception " +ie);
				}finally {
					System.out.println(" [x] Done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	
	}
	
	//fake task execution time
	private static void doWork(String task) throws InterruptedException
	{
		for(char c: task.toCharArray()) {
			try{
			if(c == '.') Thread.sleep(100);
			} catch (InterruptedException _ignored) {
					Thread.currentThread().interrupt();
				}
		}
		
			
	}
	
}