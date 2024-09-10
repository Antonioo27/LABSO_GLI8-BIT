import java.io.*;
import java.net.*;

public class TopicsHandler implements Runnable {
    DataServer dServer;
    Socket socket;
    Thread ServerRec;

    public TopicsHandler(Socket s, DataServer ds, Thread sv){
        this.socket=s;
        this.dServer=ds;
        this.ServerRec=sv;
    }

   

    public void run(){//chiaramente si dovranno creare altri thread che gestiscano publisher e subscribe
        try{
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      while (true) {
        
        String comandi=in.readLine();

        if(comandi.equalsIgnoreCase("show")){
            synchronized (this.dServer) {
                try{
                    PrintWriter pw=new PrintWriter(this.socket.getOutputStream());
                    pw.println("show: "+this.dServer.partecipanti.keySet());
                    pw.flush();
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
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
        synchronized (this.dServer) {
            this.dServer.addName(riga[0], riga[1], riga[2]);//nomeUtente, ruolo, topic
        }
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
            System.out.println("avvertenza: ");
            try {
                Thread.sleep(1500);
                //piccolo intervallo che non si nota ma serve così che il sender riesca a rilevare la interrupt
            } catch (InterruptedException e) {
                break;
            }
            if(!this.ServerRec.isAlive())
            break;

      }
        
    }catch(IOException e){
        System.err.println(e.getMessage());
    }catch(NullPointerException e){

    }finally{
       System.out.println("fine thread topic handler");
    }
}

}        

    

