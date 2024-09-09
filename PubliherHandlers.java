
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
            String[] parole=parola.split(" ");//terna nome, "sent", testo oppure duadle nome,"comando"
            String host=parole[0];
            String[] riga=this.publisher.split(" ");//terna nome, ruolo, topic
                switch(parole[1]) {
                    case "sent": 
                    /* 
                    System.out.println("invio sent");
                    System.out.println("parola=\t"+parola);//terna nome, "sent", testo
                    System.out.println("autore=\t"+this.publisher);
                    */  
                    parole=Arrays.copyOfRange(parole, 2, parole.length);
                    String testo="";
                    for (String s:parole)
                    testo=testo+"\t"+s;
                    riga[2]=riga[2].trim();
                    Messaggi mess=null;
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDateTime = now.format(formatter);
                    if(this.ds.chats.get(riga[2])==null){
                    this.ds.addMessage(0, riga[0], testo,riga[2],formattedDateTime);
                    mess=new Messaggi(0,riga[0],testo,formattedDateTime);
                    }else{
                        this.ds.addMessage(this.ds.chats.get(riga[2]).size(), riga[0], testo,riga[2],formattedDateTime);
                    mess=new Messaggi(this.ds.chats.get(riga[2]).size(),riga[0],testo,formattedDateTime);
                    }
                    //System.out.println("il messaggio compare come:\t"+mess.toString());
                    //pw.println("sent: "+mess.toString()+"\n"+"END"); 
                    //pw.flush();
                    break;

                    case "list":
                    //System.out.println("invio list");
                    ArrayList<Messaggi> temp=new ArrayList<Messaggi>();
                    for(Messaggi m : this.ds.chats.get(riga[2])){
                        if(m.sender.equalsIgnoreCase(host))
                        temp.add(m);
                    }
                    pw.println("list:\n"+temp.toString()); 
                    pw.println("END");
                    pw.flush();
                    break;

                    case "linstall":
                    //System.out.println("invio linstall");
                    pw.println("list:\n"+this.ds.chats.get(riga[2]).toString()); 
                    pw.println("END");
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
