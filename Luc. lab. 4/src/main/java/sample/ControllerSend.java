package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;



public class ControllerSend {
    @FXML
    private TextArea subjectText;
    @FXML
    private TextArea bodyText;
    @FXML
    private TextArea attText;

    @FXML
    public Label htmlLabel1;

    @FXML
    public Label htmlLabel2;

    @FXML
    public Label l1a;

    @FXML
    public Label l2a;

    @FXML
    public TextArea urlTextArea;






    public String subject = new String();
    public String body = new String();
    //back
    public void sceneHandler1() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        sample.Main.window.setScene(new Scene(root,1000,1000));
    }
    //simpleSend
    public void sendMess(){
         subject= subjectText.getText();
         body= bodyText.getText();
       // System.out.println(subject);
        simpleSend("udanny95@gmail.com","simpleEmailSender@gmail.com",subject,body );



    }
    public void simpleSend(String dest,String source, String subject, String body){

        // Recipient's email ID needs to be mentioned.
        String to = dest;

        // Sender's email ID needs to be mentioned
        String from = source;

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);//smtp.gmail.com

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            //sendID.setText("Trimis cu succes!");

        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    public void sendHtmlMess() throws IOException {
        subject= subjectText.getText();
        body= bodyText.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/att.fxml"));
        loader.setController(this);
        Parent root = (Parent) loader.load();
        sample.Main.window.setScene(new Scene(root,1000,1000));
        htmlLabel1.setText(subject);
        htmlLabel2.setText(body);
        //htmlSend("udanny95@gmail.com","simpleEmailSender@gmail.com",subject,body );




    }


    public void clickHTML() throws IOException {

        String cod =  attText.getText();
        subject= htmlLabel1.getText();
        System.out.println(subject);
        body= htmlLabel2.getText();
        System.out.println(subject);
        htmlSend("vion95@gmail.com","simpleHTML@gmail.com",  subject,  body, cod);
        sceneHandler1();//back

    }
    public void htmlSend(String dest,String source, String subject, String body, String cod) throws IOException {
        // Recipient's email ID needs to be mentioned.
        String to = dest;

        // Sender's email ID needs to be mentioned
        String from = source;

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Send the actual HTML message, as big as you like
            message.setContent(cod, "text/html");


            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }



    }
    public void addFile() throws IOException {
        subject= subjectText.getText();
        body= bodyText.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/att2.fxml"));
        loader.setController(this);
        Parent root = (Parent) loader.load();
        sample.Main.window.setScene(new Scene(root,1000,1000));
        l1a.setText(subject);
        l2a.setText(body);

    }

    public void sendFisier() throws IOException {

        String cod =  urlTextArea.getText();
        subject= l1a.getText();
        body= l2a.getText();
        attSend("vion95@gmail.com","simpleAttachment@gmail.com",  subject,  body, cod);
        sceneHandler1();//back

    }
    public void attSend(String dest,String source, String subject, String body, String cod) throws IOException{
        // Recipient's email ID needs to be mentioned.
        String to =dest;

        // Sender's email ID needs to be mentioned
        String from = source;

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(body);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = cod;
            DataSource source2 = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source2));
            messageBodyPart.setFileName("sendFile.txt");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart );

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
