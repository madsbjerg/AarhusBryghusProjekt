package Application.Models;

public class Diverse extends Vare {

    private String beskrivelse;

    private Diverse(String navn, int pant, Varetype type, String beskrivelse){
        super(navn, pant, type);
        this.beskrivelse = beskrivelse;
    }

    public void createBeklædning(String navn, String beskrivelse){
        new Diverse(navn, 0, Varetype.BEKLÆDNING, beskrivelse);
    }

    public void createMalt(String navn, String beskrivelse){
        new Diverse(navn, 0, Varetype.MALT,beskrivelse);
    }

    public void createGlas(String navn, String beskrivelse){
        new Diverse(navn, 0, Varetype.GLAS,beskrivelse);
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }
}
