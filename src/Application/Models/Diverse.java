package Application.Models;

public class Diverse extends Vare {

    private String beskrivelse;

    private Diverse(String navn, int pant, Varetype type, String beskrivelse){
        super(navn, pant, type);
        this.beskrivelse = beskrivelse;
    }

    public void CreateBeklædning(String navn, int pant, String beskrivelse){
        new Diverse(navn, pant, Varetype.BEKLÆDNING, beskrivelse);
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }
}
