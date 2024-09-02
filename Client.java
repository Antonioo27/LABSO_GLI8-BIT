import java.net.*;
import java.util.Scanner;
import java.io.*;
public class Client{
    public static void main(String[] args) {
    if(args.length!=2){
        System.err.println("Inaserire correttamente ed in ordine: nome e numero di porta di connnessione");
        return;
    }
    
    String host=args[0];
    final int porta=Integer.parseInt(args[1]);
    try{
    Socket s=new Socket(host,porta);
    System.out.println("Connessione riuscita!!!");
    System.out.println("digitare un comando:");
    //una volta accettata la connessione il server verificherà che il client si sia già loggato
    Thread clients=new Thread(new Sender(s, host));
    Thread receiver=new Thread(new Receiver(s));
    clients.start();
    receiver.start();
    try {
        clients.join();
        receiver.join();
        s.close();
        System.out.println("Socket closed.");
    } catch (InterruptedException e) {
        return;
    }


    }catch(IOException e){
        System.err.println(e.getMessage());
    }
}
}