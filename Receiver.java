import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Receiver implements Runnable {

    Socket s;

    public Receiver(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            Scanner from = new Scanner(this.s.getInputStream());
            while (true) {
                if(!from.hasNextLine())
                return;
                String response = from.nextLine();
                if(response.equalsIgnoreCase("quit")){
                    s.close();
                return;
                }else{
                    String[] responses=response.split(":");
                    switch(responses[0]){
                        case "show":        
                            System.out.println("Topics: ");
                            response=responses[1].substring(2, responses[1].length()-1);
                            String[] topics=response.split(",");
                            for(String s:topics)
                            System.out.println("-"+s);
                            break;
                        case "sent":
                        System.out.println(response);
                
                    }
                }
                /*
                System.out.println("Topics: ");
                response=response.substring(1, response.length()-1);
                String[] topics=response.split(",");
                for(String s:topics)
                System.out.println("-"+s);
                */
                }
        } catch (IOException e) {
            System.err.println("IOException caught: " + e);
            e.printStackTrace();
        } 
        
    }
}

