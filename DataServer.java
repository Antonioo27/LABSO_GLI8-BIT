import java.util.ArrayList;
import java.util.HashMap;

public class DataServer {
     HashMap<String,ArrayList<Utente>> partecipanti;//importante per tenere traccia topic e partecipanti, ma l' azione è delegato al thread
     HashMap<String,ArrayList<Messaggi>> chats;//importante per tenere traccia di topic e messaggi inviati ad ogniuno di essi

     public DataServer(HashMap<String,ArrayList<Utente>> p,  HashMap<String,ArrayList<Messaggi>> c){
        this.partecipanti=p;
        this.chats=c;
     }

     public void addName(String name, String role, String topic){
        Utente u=new Utente(name, role);
        if(this.partecipanti.containsKey(topic))//esiste già un topic con questo nome
        this.partecipanti.get(topic).add(u);
        else{
            ArrayList<Utente> first=new ArrayList<Utente>();
            first.add(u);
            this.partecipanti.put(topic,first);
        }
    }
    public void addMessage(int id, String sender, String contenuto, String topic){
Messaggi message=new Messaggi(id, sender, contenuto);
if(this.chats.containsKey(topic))//esiste già un topic con questo nome
        this.chats.get(topic).add(message);
        else{
            ArrayList<Messaggi> first=new ArrayList<Messaggi>();
            first.add(message);
            this.chats.put(topic,first);
        }
    }
}
