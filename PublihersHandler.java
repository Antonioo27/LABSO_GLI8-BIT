import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class PublihersHandler implements Runnable {
    HashMap<String,ArrayList<Messaggi>> chats;
    String publisher;
    Socket socket;

    public PublihersHandler(HashMap<String,ArrayList<Messaggi>> c, String author, Socket s){
chats=c;
publisher=author;
socket=s;
    }

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw=new PrintWriter(socket.getOutputStream());
            String riga=in.readLine();
            String[] parole=riga.split(" ");
            switch(parole[0]){
                case "sent": pw.println(riga); 
                pw.flush();
                break;
                case "list":pw.println(riga); 
                pw.flush();
                break;
                case "linstall":pw.println(riga); 
                pw.flush();
                break;
                case "quit":pw.println(riga); 
                pw.flush();
                break;
                default:
                System.err.println("COMANDO NON VALIDO");
            }

        }catch(IOException e){
            System.err.println("PublishersHandler: IOException caught: " + e);
            e.printStackTrace();
        }
    }
    
}
