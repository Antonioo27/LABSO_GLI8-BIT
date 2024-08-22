import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Server {
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
        Scanner scan=new Scanner(socket.getInputStream());
        Scanner from=new Scanner(System.in);
        boolean closed = false;
            while (!closed) {
                String request = scan.nextLine();
                if(request.equalsIgnoreCase("quit"))
                closed=true;
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
