import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Server {
    ArrayList<String> topics=new ArrayList<String>();
    public static void main(String[]args){
        if(args.length!=1){
            System.err.println("Inserire solo il numero di porta della Socket");
            return;
        }
        int porta=Integer.parseInt(args[0]);
        try{
        ServerSocket server=new ServerSocket(porta);
        System.out.println("In attesa di un client...");
        Socket socket=server.accept();
        server.close();
        System.out.println("Connection has happened with succesfull");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner from=new Scanner(System.in);
            while (true) {
                String request = in.readLine();
                if (request == null) {
                    System.out.println("Connessione chiusa dal client.");
                    break; // Uscire dal ciclo quando il flusso di input è chiuso
                }
                if(request.equalsIgnoreCase("quit"))
                break;
                else
                System.out.println(request);
                
            }
        socket.close();
        System.out.println("Closed");
    }catch(IOException e){
          System.err.println(e.getMessage());
        }
    }
}
