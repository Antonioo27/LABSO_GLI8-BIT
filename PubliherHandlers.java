import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class PubliherHandlers implements Runnable {
    DataServer ds;
    String publisher;
    Socket socket;

    public PubliherHandlers(DataServer d, String author, Socket s){
this.ds=d;
this.publisher=author;
this.socket=s;
    }

    public void run(){
        try{
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw=new PrintWriter(socket.getOutputStream());
        boolean exit=true;//questa variabile serve a far terminare in thread in caso di comando quit
        while (exit) {
            String parola = in.readLine(); 
            System.out.println("entra nel while");
            System.out.println(parola);
            
            if (parola == null) {
                System.out.println("Connessione chiusa dal client");
                break;
            }
            else if(parola.equalsIgnoreCase("show")){
                    System.out.println("invio show");
                    pw.println("show: "+this.ds.partecipanti.keySet());
                    pw.flush();
            }
            else if(parola.equalsIgnoreCase("quit")){
                System.out.println("Scollegamento come publisher");
                exit=false;
            }else{
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
                    exit=false;
                    break;
            }
            }
        }
            System.out.println("\nfinethread publisher");
    }catch(IOException e){
            System.err.println("PublishersHandler: IOException caught: " + e);
            e.printStackTrace();
    }
    }
}
