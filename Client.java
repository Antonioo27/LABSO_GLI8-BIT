import java.net.*;
import java.io.*;

public class Client{
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Client <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            //Qui il client tenta di aprire una conessione TCP verso il server
            //specificando host e port. La chiamata e' bloccante, cio' vuol dire
            //che il programma rimane sospeso finche' non viene stabilita la connessione 
            Socket s = new Socket(host, port);
            System.out.println("Connected to server");
            System.out.println("Publisher or Subscriber");

            /*
             * Delega la gestione di input/output a due thread separati, uno per inviare
             * messaggi e uno per leggerli
             * 
             */
            Thread sender = new Thread(new Sender(s));
            Thread receiver = new Thread(new Receiver(s, sender));

            sender.start();
            receiver.start();

            try {
                /* rimane in attesa che sender e receiver terminino la loro esecuzione */
                sender.join();
                receiver.join();
                s.close();
                System.out.println("Socket closed.");
            } catch (InterruptedException e) {
                /*
                 * se qualcuno interrompe questo thread nel frattempo, terminiamo
                 */
                return;
            }

        } catch (IOException e) {
            System.err.println("IOException caught: " + e);
            e.printStackTrace();
        }
    }
}