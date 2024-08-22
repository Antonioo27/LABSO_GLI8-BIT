import java.util.Scanner;
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    Socket socket;
public ClientHandler(Socket s){
    this.socket=s;
}
public void run(){
//una volta accettata la connessione il server verificherà che il client si sia già loggato
try{
PrintWriter pw=new PrintWriter(socket.getOutputStream());
Scanner scan=new Scanner(System.in);
while (true) {
    String comand=scan.nextLine();
    String[] comands=comand.split(" ");
    String pub_sub=comands[0];
    switch(pub_sub){
        case "publisher":
        System.out.println("Hai selezionato l'opzione Publisher.");
        // Logica per gestire il caso "publisher"
        break;

    case "subscribe":
        System.out.println("Hai selezionato l'opzione Subscribe.");
        // Logica per gestire il caso "subscribe"
        break;

    case "quit":
        System.out.println("Hai selezionato l'opzione Quit.");
        // Logica per gestire il caso "quit"
        pw.println("quit");
        return;

    default:
        System.out.println("Opzione non riconosciuta.");
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

