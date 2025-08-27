package com.dailyfoot.services;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailjetClient client;
    private final String fromEmail;
    private final String fromName;

    public MailService(
            @Value("${mailjet.api.key.public}") String apiKeyPublic,
            @Value("${mailjet.api.key.private}") String apiKeyPrivate,
            @Value("${mailjet.from.email}") String fromEmail,
            @Value("${mailjet.from.name}") String fromName
    ) {

        this.client = new MailjetClient(apiKeyPublic, apiKeyPrivate);
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    public void sendAccessCodeEmail(String toEmail, String toName, int accessCode) {
        try {
            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", fromEmail)
                                            .put("Name", fromName))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", toEmail)
                                                    .put("Name", toName)))
                                    .put(Emailv31.Message.SUBJECT, "Votre code d'accès DailyFoot")
                                    .put(Emailv31.Message.TEXTPART, "Bonjour " + toName + ", votre code d'accès est : " + accessCode)
                                    .put(Emailv31.Message.HTMLPART,
                                            "<h3>Bonjour " + toName + "</h3>" +
                                                    "<p>Voici votre code d'accès : <b>" + accessCode + "</b></p>")));

            MailjetResponse response = client.post(request);
            System.out.println("Mailjet status: " + response.getStatus());
            System.out.println("Mailjet data: " + response.getData());

        } catch (MailjetException e) {
            System.err.println("Erreur d'envoi Mailjet: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
