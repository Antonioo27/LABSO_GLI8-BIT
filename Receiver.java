import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Receiver implements Runnable {

    Socket s;
    Thread sender;

    public Receiver(Socket s,Thread sender) {
        this.s = s;
        this.sender=sender;
    }

    @Override
    public void run() {
        try {
            Scanner from = new Scanner(this.s.getInputStream());
            while (true) {
                String response = from.nextLine();
                if(response.equalsIgnoreCase("quit")){
                    from.close();
                break;
                }else{
                    String[] responses=response.split(":");
                    switch(responses[0]){
                        case "show":        
                            System.out.println("Topics: ");
                            response=responses[1].substring(2, responses[1].length()-1);
                            String[] topics=response.split(",");
                            for(String s:topics){
                                s=s.trim();
                                System.out.println("-"+s);
                            }
                            
                            break;
                        case "sent":
                            System.out.println(response);
                            break;
                    }
                }
                }
                
        } catch (IOException e) {
            System.err.println("IOException caught: " + e);
            e.printStackTrace();
        
        }catch (NoSuchElementException e) {
        } finally{
            try {
                System.out.println("Receiver closed.");
                this.sender.interrupt();
                this.s.close();
            } catch (IOException e) {
                System.err.println("IOException caught: " + e);
                e.printStackTrace();
            }
        }
        
    }
}

