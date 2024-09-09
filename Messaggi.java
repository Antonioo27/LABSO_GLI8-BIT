public class Messaggi {
    int ID;
    String sender;
    String pharagraps;
    String data_ora;

    public Messaggi(int id, String autore, String contenuto){
        this.ID=id;
        this.sender=autore;
        this.pharagraps=contenuto;
    }
    public Messaggi(int id, String autore, String contenuto,String data_oraString){
        this.ID=id;
        this.sender=autore;
        this.pharagraps=contenuto;
        this.data_ora=data_oraString;
    }
    public String toString(){
        return "ID: "+this.ID+"\n"+
        "Testo:\n"+this.pharagraps+"\n"+
        "Data: "+this.data_ora ;
    }
    
}
