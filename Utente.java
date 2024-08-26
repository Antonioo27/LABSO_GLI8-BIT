public class Utente{
    boolean publisher=false;
    String name="";

public Utente(String name, String ruolo){
    this.name=name;
    this.publisher=ruolo.equalsIgnoreCase("publisher");
}
public String getName(){
    return this.name;
}
public boolean isPublisher(){
    return this.publisher;
}
}

