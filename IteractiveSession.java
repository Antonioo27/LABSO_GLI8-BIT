import java.util.ArrayList;
import java.util.Scanner;

public class IteractiveSession implements Runnable {

    DataServer dataServer;
    String topic;
    
    public IteractiveSession(DataServer ds, String topic){
        this.dataServer=ds;
        this.topic=topic;
    }

    public void run(){
        
        while (true) { 
            
                if(!this.dataServer.chats.containsKey(this.topic)){
                    System.out.println("Il topic inserito risulta inesistente, prova con un' altro");
                    break;
                }
                Scanner scan=new Scanner(System.in);
                String row=scan.nextLine();
                if(row.equalsIgnoreCase("exit"))
                break;
                String[] comand=row.split(" ");
                ArrayList<Messaggi> temp=this.dataServer.chats.get(this.topic);
    
                switch (comand[0]) {
                    case "linstall":
                        if(temp.isEmpty()){
                            System.out.println("Sono presenti 0 messaggi su questo topic: "+this.topic);
                        }else{
                            System.out.println("Sono presenti "+temp.size()+" messaggi su questo topic: "+this.topic);
                            for(Messaggi m : temp)
                                System.out.println(m.toString());
                            
                        }
                        break;
    
                    case "delete":
                        try {
                            int id_to_delete=Integer.parseInt(comand[1]);
                            if(id_to_delete<=0)
                                System.out.println("Id non presente");
                            else{
                                int old_size=temp.size();
                                for(int i=0; i<temp.size(); i++){
                                    if(temp.get(i).ID==id_to_delete)
                                        temp.remove(i);
                                }
                                if(old_size==temp.size()){
                                    System.out.println("ID non presente su questo topic");
                                    this.dataServer.chats.put(this.topic, temp);
                                }
                                    
                                }
                            
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Dopo il comando <delete> va inserito l' ID del messaggio che si vuole cancellare");
                        }catch(NumberFormatException e){
                            System.out.println("Dopo il comando <delete> deve essere inserito un numero non una parola o un carattere");
                        }
                        break;
    
                    default:
                        System.out.println("Comando inesistente");
                }
            }
            
        
        System.out.println("Fine Sessione iterattiva lato server");
    }
    
}
