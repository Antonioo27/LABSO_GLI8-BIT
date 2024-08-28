import java.io.*;
import java.net.*;
import java.util.Scanner;

public class PublisherSender implements Runnable {
    Socket socket;
    String hostName="";

public PublisherSender(Socket s, String name){
    this.socket=s;
    this.hostName=name;
}

public void run(){
    try{
        Scanner scan=new Scanner(System.in);
        PrintWriter pw=new PrintWriter(socket.getOutputStream());
        while (true) {
            String comand=scan.nextLine();
            if(comand.equalsIgnoreCase("quit")){
                pw.println("quit");
                pw.close();
                return;
            }else{
            pw.println(comand);
            pw.flush();
            }
        }
        } catch (IOException e) {
            System.err.println("IOException caught: " + e);
            e.printStackTrace();
        } 
        
        }
            
        
        }
        
        
