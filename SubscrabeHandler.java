import java.io.*;
import java.net.Socket;

public class SubscrabeHandler implements Runnable {
    
    Socket socket;
    DataServer ds;
    String topic;

    public SubscrabeHandler(Socket s, DataServer ds, String topic){
        this.socket=s;
        this.ds=ds;
        this.topic=topic;
    }
    
    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw=new PrintWriter(socket.getOutputStream());
            boolean exit=true;
            while(exit){
                String parola = in.readLine(); //ricorda che se non è quit allora sarà una coppia nome_host, comando
            
                if (parola == null) {
                    System.out.println("Connessione chiusa dal client");
                    break;
                }
                else if(parola.equalsIgnoreCase("quit")){
                    System.out.println("Scollegamento come subscriber");
                exit=false;
                
                }
                else{
                    String[] parole=parola.split(" ");
                    switch (parole[1]) {
                        case "linstall":
                        //System.out.println(this.topic);
                        if(this.ds.chats.get(this.topic)==null||this.ds.chats.get(this.topic).isEmpty()){
                            pw.println("errore:ancora nessun messaggio pubblicato");
                            pw.flush();
                        }
                        else{
                            pw.println("list:\n"+this.ds.chats.get(this.topic).toString()); 
                            pw.println("END");
                            pw.flush();
                        } 
                        break;

                        default:
                            System.out.println("hai usato il comando\t"+parola);
                            System.out.println("COMANDO INESISTENTE");
                            pw.println("errore: hai usato come subscribe un comando non disponibile o disponibile solo nel caso di publisher");
                            pw.flush();
                    }

                }
            }
        } catch (IOException e) {
            System.err.println("SubsrcibeHandler: IOException caught: " + e);
            e.printStackTrace();
        }
        
    }
}
