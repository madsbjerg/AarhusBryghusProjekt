package Application.Models;

public class Diverse extends Vare {

    private String beskrivelse;

    public Diverse(String navn, int pant, Varetype type, String beskrivelse){
        super(navn, pant, type);
        this.beskrivelse = beskrivelse;
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }
}
