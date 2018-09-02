import com.rabbitmq.client.*;
import java.io.IOException;

public class EmitLogDirect {

	private static final String EXCHANGE_NAME = "direct_logs";
	
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		String severity = getSeverity(argv);
		String message = getMessage(argv);
		
		channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));  //severity is routing key here
		System.out.println(" [x] Sent '" +severity + "':'" + message + "'");
		
		channel.close();
		connection.close();
	}
	
	private static String getSeverity(String[] strings)
	{
		if(strings.length < 1)
			return "info";
		return strings[0];
	}
	
	private static String getMessage(String[] strings)
	{
		if(strings.length < 2)
			return "Hello! your stirng is less than 1 length";
			
			return jointStrings(strings, " ", 1);
		
	}
	
	private static String jointStrings(String[] strings, String delimeter, int start_index)
	{
		int length = strings.length;
		if(length == 0) return "";
		if(length < start_index) return "";
		StringBuilder words = new StringBuilder(strings[start_index]);
		for(int i=start_index;i< length; i++)
			words.append(delimeter).append(strings[i]);
		
		return words.toString();
	}
}