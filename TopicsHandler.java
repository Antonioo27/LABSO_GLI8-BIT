
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
            pw.println("show: "+this.dServer.partecipanti.keySet());
            pw.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
            }else if(comandi.equalsIgnoreCase("quit")){
            try {
                PrintWriter pw=new PrintWriter(this.socket.getOutputStream());
                pw.println("quit");
                pw.flush();
                this.socket.close();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
            else{
        String[] riga=comandi.split(" ");//riga[0] corrispnde all' utente
        this.dServer.addName(riga[0], riga[1], riga[2]);//nomeUtente, ruolo, topic
                switch(riga[1]){
                    case "publisher": 
                    Thread pb=new Thread(new PubliherHandlers(this.dServer , comandi, socket)) ;
                    pb.start();
                    try{
                        pb.join();    
                    }catch(InterruptedException e){
                        return;
                    }
                    break;
                    case "subscribe": System.out.println("Thread subscribe ancora da definire");
                    Thread sb=new Thread(new SubscrabeHandler(socket, dServer, riga[2]));
                    sb.start();
                    try {
                        sb.join();
                    } catch (InterruptedException e) {
                        return;
                    }
                    break;
                    default: System.err.print("ERRORE NEL SISTEMA \n RIPROVA");

                }
        }
        System.out.println("fine thread");
        
    
}

}        

    

