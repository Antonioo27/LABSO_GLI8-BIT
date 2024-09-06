import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Sender implements Runnable {
    Socket socket;
    String hostName="";
public Sender(Socket s, String name){
    this.socket=s;
    this.hostName=name;
}

@Override
    public void run(){
//una volta accettata la connessione il server verificherà che il client si sia già loggato

Scanner scan=new Scanner(System.in);
try{

PrintWriter pw=new PrintWriter(socket.getOutputStream(),true);
while (true) {
        if(Thread.currentThread().isInterrupted()||this.socket.isClosed()){
            pw.close();
            scan.close();
            break;
        }
    String comand=scan.nextLine();
    String[] comands=comand.split(" ");
  
    String pub_sub=comands[0];
    
    if(pub_sub.equalsIgnoreCase("quit")){
        pw.println(comand);
        pw.flush();
    }
    else if(pub_sub.equalsIgnoreCase("show")){
        pw.println(comand);
        pw.flush();
    }else{
        pw.println(this.hostName+" "+comand);
        pw.flush();
    }

    try {
        Thread.sleep(100);
        //piccolo intervallo che non si nota ma serve così che il sender riesca a rilevare la interrupt
    } catch (InterruptedException e) {
        break;
    }
}
} catch (IOException e) {
    System.err.println("IOException caught: " + e);
    e.printStackTrace();
} finally{
    System.out.println("Sender closed.");
}

}
    

}

