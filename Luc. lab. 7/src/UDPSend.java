import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class UDPSend {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        DatagramPacket outPacket = null;
        byte[] outBuf;
        final int PORT = 8888;

        try {
            socket = new DatagramSocket();
            //long counter = 0;
            String msg;

            while (true) {
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                msg = "This is multicast! " + timeStamp + "\n" + CPU.getInfo();
                //counter++;
                outBuf = msg.getBytes();

                //Send to multicast IP address and port
                InetAddress address = InetAddress.getByName("224.2.2.3");
                outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);

                socket.send(outPacket);

                System.out.println("Server sends : " + msg);

                //System.out.println(CPU.getInfo());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

}
