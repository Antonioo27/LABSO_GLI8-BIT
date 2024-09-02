import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Sender implements Runnable {
    Socket socket;
    String hostName="";
public Sender(Socket s, String name){
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
    if(pub_sub.equalsIgnoreCase("quit")){
        pw.close();
        socket.close();
    return;
    }
    else if(pub_sub.equalsIgnoreCase("show")){
        pw.println(comand);
        pw.flush();
    }else{

        pw.println(this.hostName+" "+comand);
        pw.flush();
    }
}

} catch (IOException e) {
    System.err.println("IOException caught: " + e);
    e.printStackTrace();
} 

}
    

}

