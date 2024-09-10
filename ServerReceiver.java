
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;

public class ServerReceiver implements Runnable {
    
    DataServer dataServer;
    Socket socket;

    public ServerReceiver(DataServer ds, Socket s){
        this.dataServer=ds;
        this.socket=s;
    }

    public void run(){
        try{
        Scanner scan=new Scanner(System.in);
        PrintWriter pw=new PrintWriter(this.socket.getOutputStream());
        while (true) { 
            String command=scan.nextLine();
            if(command.equalsIgnoreCase("quit")){//facendo ciò il receiver del client riceve il messaggio quit ed esce da tutto
                pw.println("quit server");
                pw.flush();
                scan.close();
                break;
            }
            String[] commands=command.split(" ");
            switch (commands[0]) {
                case "show":
                    Set<String> topics=this.dataServer.partecipanti.keySet();
                    System.out.println("Topics:");
                    for(String s : topics)
                    System.out.println("-"+s);
                    break;

                case "inspect":
                    System.out.println("thread inspect da definire");
                    break;
                default:
                    System.err.println("Questo comando non esiste");
            }
        }
        System.out.println("Server receiver thread terminato");
    }catch(IOException e){
        System.err.println(e.getMessage());
    }
}
}
