import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ChatServer {
    private static final int PORT = 9001;
    private static HashSet<String> names = new HashSet<String>();   //nume
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();   //stocarea informatiei

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start(); //porneste listning thread
            }
        } finally {
            listener.close();
        }
    }


    private static class Handler extends Thread {   //clasa ce extinde thread
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;


        public Handler(Socket socket) {
            this.socket = socket;
        }


        public void run() {
            try {
                //inregistrare client
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //parsam ip din client
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = new PrintWriter(socket.getOutputStream(), true);//stocarea ip in obiect

                while (true) {
                    out.println("SUBMITNAME"); //apeleaza la chatclient pentru nume
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) { //verifica daca numele nu este deja ocupat
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }
                out.println("NAMEACCEPTED"); //acceptarea raspunsului
                writers.add(out); //adaugarea informatiei in obiect

                //acceptarea mesajelor client
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) { //pentru fiecare writer, citeste mesajele
                        //RegEx --inmultire
                        String checker = "(--inmultire )(\\d+$)";
                        Pattern p1 = Pattern.compile(checker);
                        Matcher m1 = p1.matcher(input);
                        if(m1.find()){
                            int i = Integer.parseInt(m1.group(2))*2;
                            writer.println("MESSAGE " + name + ": " + i);
                        }else {
                            writer.println("MESSAGE " + name + ": " + input);
                        }


                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
