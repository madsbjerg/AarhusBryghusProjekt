package Application.Models;

public class Diverse extends Vare {

    private String beskrivelse;

    private Diverse(String navn, int pant, Varetype type, String beskrivelse){
        super(navn, pant, type);
        this.beskrivelse = beskrivelse;
    }

    public void createBeklædning(String navn, int pant, String beskrivelse){
        new Diverse(navn, pant, Varetype.BEKLÆDNING, beskrivelse);
    }

    public void createMalt(String navn, int pant, String beskrivelse){
        new Diverse(navn, pant, Varetype.MALT,beskrivelse);
    }

    public void createGlas(String navn, int pant, String beskrivelse){
        new Diverse(navn, pant, Varetype.GLAS,beskrivelse);
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }
}
