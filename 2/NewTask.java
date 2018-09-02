import com.rabbitmq.client.*;
public class NewTask{
	private final static String QUEUE_NAME = "task_queue";
	
	public static void main(String[] args)throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory();
		
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		boolean durable = true;
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		String message = getMessage(args);
		
		channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
		}System.out.println(" [x] Sent '" + message + "'");
		
		//closing connections
		channel.close();
		connection.close();
	}
	
	private static String getMessage(String[] strings){
		if(strings.length < 1)
			return "Hello World!, your string is less than 1 length";
		return joinStrings(strings, " ");
	}
	
	private static String joinStrings(String[] strings, String delimiter)
	{
		int length = strings.length;
		if(length == 0) return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for(int i=1; i< length; i++)
		{
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
	
}