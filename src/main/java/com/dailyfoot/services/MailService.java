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

            String subject = "Bienvenue sur DailyFoot - Votre code d'accès";

            String textPart = "Bonjour " + toName + ",\n\n"
                    + "Bienvenue sur DailyFoot !\n"
                    + "Voici votre code d'accès pour vous connecter : " + accessCode + "\n\n"
                    + "Conservez-le précieusement et ne le partagez avec personne.\n\n"
                    + "L'équipe DailyFoot";

            String htmlPart = "<!DOCTYPE html>"
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

            // Construction de la requête Mailjet
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
                                    .put(Emailv31.Message.SUBJECT, subject)
                                    .put(Emailv31.Message.TEXTPART, textPart)
                                    .put(Emailv31.Message.HTMLPART, htmlPart)
                            ));

            MailjetResponse response = client.post(request);

            System.out.println("Mail envoyé à " + toEmail + " avec le statut : " + response.getStatus());

        } catch (MailjetException e) {
            System.err.println("Erreur d'envoi Mailjet: " + e.getMessage()); // a capter dans les exceptions
            e.printStackTrace();
        }
    }

}
