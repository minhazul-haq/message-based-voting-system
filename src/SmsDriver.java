//sms sender & receiver. class that over-rides methods of OzSmsClient

import java.io.*;
import hu.ozeki.*;

public class SmsDriver extends OzSmsClient
{
    private QueryReply queryReply;

    public SmsDriver(String host, int port) throws IOException, InterruptedException
    {
		super(host, port);
		login("admin","abc123");
        queryReply = new QueryReply();
    }

	@Override
    public void doOnMessageAcceptedForDelivery(OzSMSMessage sms)
    {
		System.out.println(sms.messageData + " ... accepted for delivery");
    }

    @Override
    public void doOnMessageDeliveredToHandset(OzSMSMessage sms)
    {
		System.out.println(sms.messageData + " ... has been delivered to handset");
    }

    @Override
    public void doOnMessageDeliveredToNetwork(OzSMSMessage sms)
    {
		System.out.println(sms.messageData + " ... has been sent");
    }

    @Override
    public void doOnMessageDeliveryError(OzSMSMessage sms)
    {
		System.out.println(sms.messageData + " ... could not be delivered");
    }

    @Override
    public void doOnMessageReceived(OzSMSMessage sms)
    {
		Ongoing.newSms = true;
		System.out.println("newSms : " + Ongoing.newSms);
    	replySMS(sms.messageData.toString(), sms.sender.toString());
        System.out.println("Message received ... Sender address: " + sms.sender +
			", Message text: " + sms.messageData  + ", Receiving time: "+ sms.receivedDate);
    }

    @Override
    public void doOnClientConnectionError(int errorCode, String errorMessage)
    {
        System.out.println("Error! ... Errorcode: " + errorCode + ", ErrorMessage: " + errorMessage);
    }
	
    //reply to client when gets a sms
    public void replySMS(String message,String sender)
    {
		message = message.toLowerCase();
        sender = sender.substring(3,14);
		String reply;
		
		try
        {
            if (message.equals("info"))
            {
                System.out.println("Client >> info");
                reply = new String(queryReply.smsInfo());
                sendMessage(sender, reply);
                System.out.println("Reply  >> " + reply);
            }
            else if (message.equals("result"))
            {
                System.out.println("Client >> result");
                reply = new String(queryReply.smsResult());
                sendMessage(sender, reply);
                System.out.println("Reply  >> " + reply);
            }
            else
			{
                System.out.println("Client >> " + message);
                reply = new String(queryReply.smsVote(sender,message));
                sendMessage(sender, reply);
                System.out.println("Reply  >> " + reply);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
}
