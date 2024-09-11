package com.airbnb.service.implementationClass;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @Value("${twilio.whatsapp-number}")
    private String whatsappnumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }
    public void sendSms(String toPhoneNumber, String messageBody) {
        // Automatically add country code if not already present
        String defaultCountryCode = "+91";  // Change to your desired default country code

        // If the phone number doesn't start with '+', prepend the default country code
        if (!toPhoneNumber.startsWith("+")) {
            toPhoneNumber = defaultCountryCode + toPhoneNumber;
        }

        Message.creator(
                        new PhoneNumber(toPhoneNumber),  // Recipient's phone number with country code
                        new PhoneNumber(twilioPhoneNumber),  // Your Twilio phone number
                        messageBody)
                .create();
    }
    public void sendWhatsAppMessage(String to, String messageBody) {
        // Automatically add country code if not already present
        String defaultCountryCode = "+91";  // Change to your desired default country code

        // If the phone number doesn't start with '+', prepend the default country code
        if (!to.startsWith("+")) {
            to = defaultCountryCode + to;
        }


        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:"+to),
                        new com.twilio.type.PhoneNumber("whatsapp:"+whatsappnumber),  // Twilio number
                        messageBody)
                .create();

//        System.out.println("Message sent with SID: " + message.getSid());
    }


}

