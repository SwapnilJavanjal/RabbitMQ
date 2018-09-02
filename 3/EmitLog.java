import java.io.IOException;
import com.rabbitmq.client.*;

public class EmitLog {
	private static final String EXCHANGE_NAME = "logs";
	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String message = getMessage(args);
		
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent '" + message +"'");
	
		channel.close();
		connection.close();
		//} catch(Exception e) {}
	}
	
	private static String getMessage(String[] strings)
	{
		if(strings.length < 1)
			return "Hello! your stirng is less than 1 length";
			
			return jointStrings(strings, " ");
		
	}
	
	private static String jointStrings(String[] strings, String delimeter)
	{
		int length = strings.length;
		if(length == 0) return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for(int i=0;i< length; i++)
			words.append(delimeter).append(strings[i]);
		
		return words.toString();
	}
}
