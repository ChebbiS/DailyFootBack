package com.dailyfoot.services;

import com.mailjet.client.ClientOptions;
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

    private final MailjetClient mailjetClient;

    public MailService(
            @Value("${mailjet.api.key.public}") String apiKeyPublic,
            @Value("${mailjet.api.key.private}") String apiKeyPrivate
    ) {
        this.mailjetClient = new MailjetClient(apiKeyPublic, apiKeyPrivate, new ClientOptions("v3.1"));
    }

    public void sendEmail(String toEmail, String toName, String subject, String textPart, String htmlPart) throws MailjetException {

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "no-reply@dailyfoot.com")
                                        .put("Name", "DailyFoot"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)
                                                .put("Name", toName)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.TEXTPART, textPart)
                                .put(Emailv31.Message.HTMLPART, htmlPart)
                        )
                );

        MailjetResponse response = mailjetClient.post(request);
        System.out.println("Status: " + response.getStatus());
        System.out.println("Response: " + response.getData());
    }
}
