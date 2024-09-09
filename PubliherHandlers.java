
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

                    //BISOGNERA' POI INVIARE IL MESSAGGIO A TUTTI I SUBSCRIBE CONNESSI
                    break;

                    case "list":
                    //System.out.println("invio list");
                    if(this.ds.chats.get(riga[2])==null||this.ds.chats.get(riga[2]).isEmpty()){
                        pw.println("errore:nessun messaggio pubblicato");
                        pw.flush();
                    }
                    else{
                        ArrayList<Messaggi> temp=new ArrayList<Messaggi>();
                    for(Messaggi m : this.ds.chats.get(riga[2])){
                        if(m.sender.equalsIgnoreCase(host))
                        temp.add(m);
                    }
                        if(temp.isEmpty()){
                            pw.println("errore:non hai ancora pubblicato nessun messaggio su questo topic");
                            pw.flush();
                        }else{
                            pw.println("list:\n"+temp.toString()); 
                            pw.println("END");
                            pw.flush();
                        }
                    
                    }
                    break;
                    case "linstall":
                    //System.out.println("invio linstall");
                    if(this.ds.chats.get(riga[2])==null||this.ds.chats.get(riga[2]).isEmpty()){
                        pw.println("errore:nessun messaggio pubblicato");
                        pw.flush();
                    }else{
                        pw.println("list:\n"+this.ds.chats.get(riga[2]).toString()); 
                        pw.println("END");
                        pw.flush();    
                    }
                    
                    break;

                    default:
                    System.out.println("hai usato il comando\t"+parola);
                    System.out.println("COMANDO INESISTENTE");
                    pw.println("errore: hai usato, nel ruolo di publisher, un comando non disponibile");
                    pw.flush();
                    
                    
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
