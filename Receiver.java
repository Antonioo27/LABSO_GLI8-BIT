import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


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
            BufferedReader from = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            while (true) {
                /*
                 * try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
                 */
                
                String response = from.readLine();
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
                        case "list":
                            System.out.println(">list");
                            String messaggi_interi="";
                            String line=responses[1];
                            while(!line.equalsIgnoreCase("END")){
                                line=from.readLine();
                                messaggi_interi=messaggi_interi+line+"\n";
                            }
                            System.out.println(messaggi_interi);
                            break;
                    }
                }
                }
                
        } catch (IOException e) {
            System.err.println("IOException caught: " + e);
            e.printStackTrace();
        
        }catch (NullPointerException e) {
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

