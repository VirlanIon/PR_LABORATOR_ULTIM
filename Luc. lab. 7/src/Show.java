import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Show {
    private static JTextArea label;
    public static JFrame frame;

    private static void createAndShowGUI() {
         frame = new JFrame("Stream");

         label = new JTextArea("This is a Swing frame");
        label.setText("Wait connection");
        label.setSize(600,600);

        frame.add(label);

        frame.setSize(600, 600); // width=350, height=200
        frame.setVisible(true); // Display the frame
    }
    public static void setString(String s){

        label.setText(s);
        //frame.add(label);
    }
    public static void createApp(){
        createAndShowGUI();
    }
    public static void main(String[] args) throws AWTException, IOException {
        createAndShowGUI();
        setString("ss");

    }
}
