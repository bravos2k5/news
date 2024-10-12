package com.bravos.news.utils;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailUtil {

    private static final String email = "steak@quocbao2k5.id.vn";
    private static final String password = "Steak2005@";
    private static final String host = "163.44.207.67";
    private static final String port = "587";
    private static final Properties props = getProps();
    private static final Authenticator authenticator = new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(email,password);
        }
    };

    private static Properties getProps() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port",port);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth","true");
        return props;
    }

    public static void sendEmail(String to, String subject, String text) {
        sendEmail(to,subject,text,null);
    }

    public static void sendEmail(String to, String subject, String text, List<String> fileNames) {
        Session session = Session.getInstance(props,authenticator);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type","text/HTML; charset=UTF-8");
            msg.setFrom(email);
            msg.setSubject(subject);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSentDate(new Date());

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(text);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);

            if (fileNames != null) {
                for (String fileName : fileNames) {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(fileName);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    attachmentBodyPart.setFileName(new File(fileName).getName());
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            msg.setContent(multipart);

            Transport.send(msg);

        } catch (MessagingException e) {
            if (e instanceof SendFailedException && e.getMessage().contains("550 5.1.1")) {
                System.err.println("Email address does not exist.");
            } else {
                System.err.println(e.getMessage());
            }
        }
    }

}
