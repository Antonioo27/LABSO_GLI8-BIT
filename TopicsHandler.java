import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
import java.net.*;
public class TopicsHandler implements Runnable {
    HashMap<String,ArrayList<Utente>> partecipanti;

    //in questo dizionario per ogni topic sarà possibile visionare tutti i suoi utenti
   
    Socket socket;
    String rigaDiComando;
    public TopicsHandler(Socket s, String frase, HashMap<String, ArrayList<Utente>> partecipanti){
        this.socket=s;
        this.rigaDiComando=frase;
        this.partecipanti=partecipanti;
    }

    public void add(String name, String role, String topic){
        Utente u=new Utente(name, role);
        if(this.partecipanti.containsKey(topic))//esiste già un topic con questo nome
        this.partecipanti.get(topic).add(u);
        else{
            ArrayList<Utente> first=new ArrayList<Utente>();
            first.add(u);
            this.partecipanti.put(topic,first);
        }
    }

    public void run(){
        
        String comandi=this.rigaDiComando;
    
        
        if(comandi.equalsIgnoreCase("show")){
            System.out.println(this.partecipanti.keySet());
            try{
            PrintWriter pw=new PrintWriter(this.socket.getOutputStream());
            pw.println(this.partecipanti.keySet());
            pw.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
            }else{
        String[] riga=comandi.split(" ");//riga[0] corrispnde all' utente
        add(riga[0], riga[1], riga[2]);
        }
        System.out.println("fine thread");
    
}

}        

    

