
import java.io.*;
import java.net.*;
public class TopicsHandler implements Runnable {
    DataServer dServer;

    //in questo dizionario per ogni topic sarà possibile visionare tutti i suoi utenti
   
    Socket socket;
    String rigaDiComando;
    public TopicsHandler(Socket s, String frase, DataServer ds){
        this.socket=s;
        this.rigaDiComando=frase;
        this.dServer=ds;
    }

   

    public void run(){//chiaramente si dovranno creare altri thread che gestiscano publisher e subscribe
        
        String comandi=this.rigaDiComando;
        
        if(comandi.equalsIgnoreCase("show")){
            //System.out.println(this.partecipanti.keySet());
            try{
            PrintWriter pw=new PrintWriter(this.socket.getOutputStream());
            pw.println(this.dServer.partecipanti.keySet());
            pw.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
            }else{
        String[] riga=comandi.split(" ");//riga[0] corrispnde all' utente
        this.dServer.addName(riga[0], riga[1], riga[2]);//nomeUtente, ruolo, topic
                switch(riga[1]){
                    case "publisher": Thread pb=new Thread(new PublihersHandler(this.dServer.chats , comandi, socket)) ;
                        pb.start();
                        try{
                            pb.join();    
                        }catch(InterruptedException e){
                            return;
                        }
                        break;
                    case "subscribe": System.out.println("Thread subscribe amncora da definire");
                    break;
                    default: System.err.print("ERRORE NEL SISTEMA \n RIPROVA");

                }
        }
        System.out.println("fine thread");
    
}

}        

    

