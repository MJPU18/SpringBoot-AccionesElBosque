package co.edu.unbosque.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsAppSender {

	private static final String ACCOUNT_SID = "AC45460f6d4fbbbd88d16e145845e85cc2";
	private static final String AUTH_TOKEN = "e80a4e418e4767d2cec90e40fab53ef5";

	public static void sendMessage(String phoneNumber, String content) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber("whatsapp:" + phoneNumber),
				new PhoneNumber("whatsapp:+14155238886"), content).create();

		System.out.println("Mensaje WhatsApp enviado: " + message.getSid());
	}
	
	public static void main(String[] args) {
		WhatsAppSender.sendMessage("+573195515557", "Funiono soy JuanPa");
	}

}
