import java.util.ArrayList;
import java.util.HashMap;

public class DataServer {
     HashMap<String,ArrayList<Utente>> partecipanti;//importante per tenere traccia topic e partecipanti, ma l' azione è delegato al thread
     HashMap<String,ArrayList<Messaggi>> chats;//importante per tenere traccia di topic e messaggi inviati ad ogniuno di essi

     public DataServer(HashMap<String,ArrayList<Utente>> p,  HashMap<String,ArrayList<Messaggi>> c){
        this.partecipanti=p;
        this.chats=c;
     }

     public synchronized void addName(String name, String role, String topic){
        Utente u=new Utente(name, role);
        if(this.partecipanti.containsKey(topic))//esiste già un topic con questo nome
        this.partecipanti.get(topic).add(u);
        else{
            ArrayList<Utente> first=new ArrayList<Utente>();
            first.add(u);
            this.partecipanti.put(topic,first);
        }
    }
    
    public synchronized void addMessage(int id, String sender, String contenuto, String topic){
Messaggi message=new Messaggi(id, sender, contenuto);
if(this.chats.containsKey(topic))//esiste già un topic con questo nome
        this.chats.get(topic).add(message);
        else{ 
            ArrayList<Messaggi> first=new ArrayList<Messaggi>();
            first.add(message);
            this.chats.put(topic,first);
        }
    }
    public synchronized void addMessage(int id, String sender, String contenuto, String topic,String data_oraString){
        Messaggi message=new Messaggi(id, sender, contenuto,data_oraString);
        if(this.chats.containsKey(topic))//esiste già un topic con questo nome
                this.chats.get(topic).add(message);
                else{ 
                    ArrayList<Messaggi> first=new ArrayList<Messaggi>();
                    first.add(message);
                    this.chats.put(topic,first);
                }
            }
    // Metodo sincronizzato per leggere i partecipanti di un topic
    public synchronized ArrayList<Utente> getPartecipanti(String topic) {
        return this.partecipanti.getOrDefault(topic, new ArrayList<>());
    }

    // Metodo sincronizzato per leggere i messaggi di un topic
    public synchronized ArrayList<Messaggi> getChats(String topic) {
        return this.chats.getOrDefault(topic, new ArrayList<>());
    }
    public void setChats(String topic,ArrayList<Messaggi> new_chat){
        this.chats.put(topic,new_chat);
    }
}
