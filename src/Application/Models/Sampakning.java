package Application.Models;

public class Sampakning extends Vare{

    private String æsketype;
    private int antalOel; 
    private int antalGlas;


    public Sampakning(String navn, int pant, String æsketype, int antalOel, int antalGlas){
        super(navn, pant, Varetype.SAMPAKNING);
        this.æsketype = æsketype;
        this.antalOel = antalOel;
        this.antalGlas = antalGlas;
    }

    public String getÆsketype() {
        return æsketype;
    }

    public int getAntalOel() {
        return antalOel;
    }

    public int getAntalGlas() {
        return antalGlas;
    }

}
