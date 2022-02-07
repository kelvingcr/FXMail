package br.com.kelvingcr.emailautosend.objects;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ObjectSendEmail {

   public String username;
   public String password;

    private String emailTo, titulo, subject, text;

    public ObjectSendEmail(String username, String password, String emailTo, String titulo, String subject, String text){
        this.username = username;
        this.password = password;
        this.emailTo = emailTo;
        this.titulo = titulo;
        this.subject = subject;
        this.text = text;
    }

    public void enviarEmail(boolean emailHtml){
        try {
            //olhe as configurações do smtp do seu email
            Properties properties = new Properties();
            properties.put("mail.smtp.ssl.trust", "*"); //Autorização
            properties.put("mail.smtp.auth", "true"); //Autorização
            properties.put("mail.smtp.starttls", "true"); //Autenticação
            properties.put("mail.smtp.host", "smtp.gmail.com"); //Servidor do gmail
            properties.put("mail.smtp.port", "465"); //porta do servidor
            properties.put("mail.smtp.socketFactory.port", "465"); //Expecifica a porta ser conectada pelo socket
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //Classe socket de conexao ao SMTP

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Address[] toUser = InternetAddress.parse(emailTo);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, titulo)); // quem está enviando
            message.setRecipients(Message.RecipientType.TO, toUser); //email de destino
            message.setSubject(subject);

            if(emailHtml) {
                message.setContent(text, "text/html; charset=utf-8");
            }else{
                message.setText(text);
            }

            Transport.send(message);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
