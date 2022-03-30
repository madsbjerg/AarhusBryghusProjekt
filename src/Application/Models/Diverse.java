package Application.Models;

public class Diverse extends Vare {

    private String beskrivelse;

    public Diverse(String navn, int pant, Varetype type, String beskrivelse){
        super(navn, pant, type);
        this.beskrivelse = beskrivelse;
    }

    public Diverse createBeklædning(String navn, String beskrivelse){
        return new Diverse(navn, 0, Varetype.BEKLÆDNING, beskrivelse);
    }

    public Diverse createMalt(String navn, String beskrivelse){
        return new Diverse(navn, 0, Varetype.MALT,beskrivelse);
    }

    public Diverse createGlas(String navn, String beskrivelse){
        return new Diverse(navn, 0, Varetype.GLAS,beskrivelse);
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }
}
