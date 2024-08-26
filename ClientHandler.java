import java.util.Scanner;
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    Socket socket;
    String hostName="";
public ClientHandler(Socket s, String name){
    this.socket=s;
    this.hostName=name;
}
public void run(){
//una volta accettata la connessione il server verificherà che il client si sia già loggato

try{
Scanner scan=new Scanner(System.in);
PrintWriter pw=new PrintWriter(socket.getOutputStream());
while (true) {
    String comand=scan.nextLine();
    String[] comands=comand.split(" ");
  
    String pub_sub=comands[0];
    switch(pub_sub){
        case "publisher":
        System.out.println("Hai selezionato l'opzione Publisher.");
        // Logica per gestire il caso "publisher"
        pw.println(this.hostName+" "+comand);
        pw.flush();
        break;

    case "subscribe":
        System.out.println("Hai selezionato l'opzione Subscribe.");
        // Logica per gestire il caso "subscribe"
        pw.println(this.hostName+" "+comand);
        pw.flush();
        break;

    case "quit":
        System.out.println("Hai selezionato l'opzione Quit.");
        // Logica per gestire il caso "quit"
        pw.println("quit");
        pw.close();
        return;
    case "show":
        System.out.println("Hai selezionato l' operazione show");
        pw.println("show");
        pw.flush();
       break;

    default:
        System.out.println("Opzione non riconosciuta.");
        System.err.println("RIPROVA!!!!");
        // Logica per gestire valori non riconosciuti
        break;
    
}

}

} catch (IOException e) {
    System.err.println("IOException caught: " + e);
    e.printStackTrace();
} 

}
    

}

