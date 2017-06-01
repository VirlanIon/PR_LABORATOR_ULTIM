package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//

public class Controller {
     int number = 5;

    @FXML
    private Button bt1;
    @FXML
    private Button bt2;

    @FXML
    private Button sendBt;
    @FXML
    private Button addHTML;
    @FXML
    private Button addAtt;






    @FXML
    public ListView<String> eList = new ListView<String>();
    ObservableList<String> items = FXCollections.observableArrayList ();
    @FXML
    public Label text1;

    @FXML
    public Label emailLabel;

    @FXML
    public WebView webView;






  public BufferedReader br;
  public Reader r;
    String username = "vion95";
    String pass = "asdf12345";
    POP3Client client = new POP3Client();

    public void getter() throws IOException {
        items.removeAll();







        client.connect( "127.0.0.1", 110 );
        if( client.login( username, pass ) )
        {
            POP3MessageInfo[] messages = client.listMessages();

            //nr mesaje
            Integer nr = messages.length;
            text1.setText(username + " , aveti " + String.valueOf(nr) + " mesaje in Inbox");

            //sender
            String sender = "(From: )(.*)";
            Pattern p1 = Pattern.compile(sender);

            //subject
            String subject = "(Subject: )(.*)";
            Pattern p2 = Pattern.compile(subject);




            for(int i = 0; i < nr; i++){

                 r = client.retrieveMessage( messages[ i ].number );
                 br = new BufferedReader( r );



                String line = null;
                String resultSe = null;
                String resultSu = null;

                while((line = br.readLine()) != null) {


                    Matcher m1 = p1.matcher(line);
                    Matcher m2 = p2.matcher(line);

                    if (m1.find()) {
                        //System.out.println("Found value: " + m1.group(2));
                        resultSe = (m1.group(2));
                    }
                    if (m2.find()) {
                        //System.out.println("Found value: " + m2.group(2));
                        resultSu = (m2.group(2));
                    }

                     else {
                        //System.out.println("NO MATCH");
                    }
                }
                if(resultSe!=null&&resultSu!=null){
                    items.add(String.valueOf(i+1).toString() + " " + resultSe + " " + resultSu + " " );
                }
            }
            eList.setItems(items);

          //  eList.getSelectionModel().getSelectedItem().equals()


        }



    }

    public void emailClicked() throws IOException, MessagingException {


        for(int i=0;i<items.size();i++){
            if (eList.getSelectionModel().getSelectedItem().equals(items.get(i))){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reader.fxml"));
                loader.setController(this);
                Parent root = (Parent) loader.load();


                sample.Main.window.setScene(new Scene(root,1000,1000));
                emailLabel.setText(String.valueOf(i+1));

                //
                POP3MessageInfo[] messagex = client.listMessages();
                r = client.retrieveMessage( messagex[ i ].number );
                br = new BufferedReader( r );
                String line = null;
                String result = null;

                webView.setVisible(false);
                while( ( line = br.readLine()) != null )
                {
                    //System.out.println(line);
                    if (line.startsWith("Content-Type: text/html"))
                    {
                        webView.setVisible(true);
                        showAtt(i,true,false);

                        System.out.println("FindHtml");
                    }
                    else {

                        result = result + line + "\n";
                    }
                    if (line.startsWith("Content-Type: text/plain; charset=us-ascii")){
                        //
                        System.out.println("FindAtt");
                        result = result + line + " \"Fisierul este salvat in : D:/Attachment/" + " \n";
                        showAtt(i,false,true);








                    }


                }
                emailLabel.setText(result);








            }

        }




    }
    public void showAtt(int i,boolean html, boolean atr) throws MessagingException, IOException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol", "imap");
        try {
            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore("pop3");//create store instance
            store.connect("127.0.0.1", username, pass);
            Folder inbox = store.getFolder("inbox");
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            inbox.open(Folder.READ_ONLY);//set access type of Inbox
            Message messages[] = inbox.search(ft);
            String mail, sub, bodyText = "";
            Object body;

            mail = messages[i].getFrom()[0].toString();
            sub = messages[i].getSubject();
            body = messages[i].getContent();
            System.out.println(mail.toString());
            //bodyText = body.....
            if (html){
                String contentType = messages[i].getContentType();
                if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                    // System.out.println("iazdesi");
                    Object content = (messages[i]).getContent();
                    if (content != null) {
                        String messageContent = content.toString();
                        //webView.getEngine().loadContent(messageContent);
                        webView.getEngine().loadContent(messageContent);
                    }
                }
            }
            if (atr){
                Multipart multiPart = (Multipart) messages[i].getContent();

                for (int a = 0; a < multiPart.getCount(); a++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(a);
                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                        part.saveFile("D:/Attachment/" + part.getFileName());
                    }
                }
            }



        }catch (Exception e) {
                System.out.println(e);
        }
    }

    /*public void showHtml(String line){

        webView.getEngine().loadContent(line);
    }*/

    //send
    public void sceneHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/send.fxml"));
        sample.Main.window.setScene(new Scene(root,1000,1000));

//        urlText.setVisible(false);
//        htmlText.setVisible(false);


    }
    //back
    public void sceneHandler1() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        sample.Main.window.setScene(new Scene(root,1000,1000));
    }



}
