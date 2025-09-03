package com.dailyfoot.services;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${mailjet.from.email}")
    private String fromEmail;
    @Value("${mailjet.from.name}")
    private String fromName;
    @Value("${mailjet.api.key.public}")
    private String apiKeyPublic;
    @Value("${mailjet.api.key.private}")
    private String apiKeyPrivate;

    private MailjetClient client;

    @PostConstruct
    public void init() {
        this.client = new MailjetClient(apiKeyPublic, apiKeyPrivate);
    }

    public void sendAccessCodeEmail(String toEmail, String toName, int accessCode) {
        try {
            String subject = "Bienvenue sur DailyFoot - Votre code d'accès";
            String textPart = generateText(toName, accessCode);
            String htmlPart = generateHTML(toName, accessCode);
            // Construction de la requête Mailjet
            MailjetRequest request = requestSendMailjet(toEmail, subject, textPart, htmlPart, toName);
            MailjetResponse response = client.post(request);
            System.out.println("Mail envoyé à " + toEmail + " avec le statut : " + response.getStatus());

        } catch (MailjetException e) {
            System.err.println("Erreur d'envoi Mailjet: " + e.getMessage()); // a capter dans les exceptions
            e.printStackTrace();
        }
    }

    private String generateHTML(String toName, int accessCode) {
        return "<!DOCTYPE html>"
                + "<html lang='fr'>"
                + "<head><meta charset='UTF-8'><title>DailyFoot</title></head>"
                + "<body style='font-family:Arial,sans-serif; line-height:1.6;'>"
                + "<h2 style='color:#2E86C1;'>Bonjour " + toName + ",</h2>"
                + "<p>Bienvenue sur <strong>DailyFoot</strong> !</p>"
                + "<p>Voici votre code d'accès pour vous connecter :</p>"
                + "<p style='font-size:18px; font-weight:bold; color:#C0392B;'>" + accessCode + "</p>"
                + "<p>Conservez-le précieusement et ne le partagez avec personne.</p>"
                + "<hr style='border:none; border-top:1px solid #ccc;'/>"
                + "<p style='font-size:12px; color:#888;'>"
                + "Si vous n'êtes pas à l'origine de cette demande, veuillez ignorer cet email."
                + "</p>"
                + "<p style='font-size:12px; color:#888;'>L'équipe DailyFoot</p>"
                + "</body></html>";
    }
private String generateText(String toName, int accessCode) {
        return "Bonjour " + toName + ",\n\n"
                + "Bienvenue sur DailyFoot !\n"
                + "Voici votre code d'accès pour vous connecter : " + accessCode + "\n\n"
                + "Conservez-le précieusement et ne le partagez avec personne.\n\n"
                + "L'équipe DailyFoot";
}
private MailjetRequest requestSendMailjet(String toEmail, String subject, String textPart, String htmlPart, String toName) {
        return new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", fromEmail)
                                        .put("Name", fromName))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)
                                                .put("Name", toName)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.TEXTPART, textPart)
                                .put(Emailv31.Message.HTMLPART, htmlPart)
                        ));
}
}
