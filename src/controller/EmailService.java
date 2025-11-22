package controller;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    // --- CONFIGURACIÓN DE GMAIL ---
    // IMPORTANTE: Aquí debes poner TU correo y TU contraseña de aplicación
    private final String REMITENTE = "mathpath.learn@gmail.com"; 
    private final String PASSWORD_APP = "kckx uaxz qbin ixtk"; // ¡OJO! No es tu clave normal, es la App Password
    
    public String generarCodigo() {
        Random rand = new Random();
        int numero = rand.nextInt(999999); 
        return String.format("%06d", numero); // Retorna algo como "048152"
    }

    public boolean enviarCorreo(String destinatario, String codigo, String nombreUsuario) {
        // 1. Propiedades del servidor SMTP de Google
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // 2. Iniciar Sesión
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, PASSWORD_APP);
            }
        });

        try {
            // 3. Construir el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de Verificación - MathPath");
            
            // Diseño HTML simple para el correo
            String htmlContent = 
                    "<div style='font-family: Arial, sans-serif; color: #333; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>" +
                    "<h2 style='color: #2E86C1;'>Bienvenido a MathPath, " + nombreUsuario + "</h2>" +
                    "<p>Estás a un paso de activar tu cuenta de Docente.</p>" +
                    "<p>Tu código de seguridad es:</p>" +
                    "<h1 style='color: #E74C3C; letter-spacing: 5px;'>" + codigo + "</h1>" +
                    "<p><small>Si no solicitaste este código, ignora este mensaje.</small></p>" +
                    "</div>";
            
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // 4. Enviar
            Transport.send(message);
            System.out.println("Correo enviado a: " + destinatario);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error enviando correo: " + e.getMessage());
            return false;
        }
    }
}