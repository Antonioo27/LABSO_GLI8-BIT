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
        String parola = in.readLine(); 
        while (true) {
            System.out.println("entra nel while");
            if (parola == null) {
                System.out.println("Connessione chiusa dal client");
                break;
            }
        String[] parole=parola.split(" ");
            
                switch(parole[1]) {
                    case "sent": 
                    System.out.println("invio sent");
                    pw.println("sent: hai selezionato il comando sent"); 
                    pw.flush();
                    break;
                    case "list":
                    System.out.println("invio list");
                    pw.println("list: hai selezionato il comando list"); 
                    pw.flush();
                    break;
                    case "linstall":
                    System.out.println("invio linstall");
                    pw.println("linstall: hai selezionato il comando linstall");  
                    pw.flush();
                    break;
                    case "quit":
                    System.out.println("Scollegamento come publisher");
                    return;
            }
            

            
            }
            System.out.println(parola+"\nfinethread publisher");
    }catch(IOException e){
            System.err.println("PublishersHandler: IOException caught: " + e);
            e.printStackTrace();
    }
}
}
