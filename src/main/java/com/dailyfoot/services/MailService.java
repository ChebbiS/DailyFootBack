package com.dailyfoot.services;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mailjet.api.key}")
    private String apiKey;

    @Value("${mailjet.api.secret}")
    private String apiSecret;

    @Value("${mailjet.sender.email}")
    private String senderEmail;

    @Value("${mailjet.sender.name}")
    private String senderName;

    public void sendAccessCode(String toEmail, String toName, int accessCode) {
        MailjetClient client = new MailjetClient(apiKey, apiSecret, new ClientOptions("v3.1"));

        JSONObject message = new JSONObject()
                .put(Emailv31.Message.FROM, new JSONObject()
                        .put("Email", senderEmail)
                        .put("Name", senderName))
                .put(Emailv31.Message.TO, new JSONArray()
                        .put(new JSONObject()
                                .put("Email", toEmail)
                                .put("Name", toName)))
                .put(Emailv31.Message.SUBJECT, "Votre code d'accès DailyFoot")
                .put(Emailv31.Message.TEXTPART, "Bonjour " + toName + ", votre code d'accès est : " + accessCode)
                .put(Emailv31.Message.HTMLPART, "<h3>Bonjour " + toName + "</h3><p>Votre code d'accès est : <b>" + accessCode + "</b></p>");

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray().put(message));

        try {
            MailjetResponse response = client.post(request);
            System.out.println("Mail status: " + response.getStatus());
            System.out.println("Mail data: " + response.getData());
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
        }
    }
}
