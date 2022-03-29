package Application.Models;

import java.util.ArrayList;

public class Sampakning extends Vare{

    private String æsketype;
    private int antalOel; 
    private int antalGlas;
    private Drikkevare[] drikkevarer;
    private int antalFyldt;                     // Hjælpe attribut til array.

    //private final ArrayList<Drikkevare> drikkevarer = new ArrayList<>();


    public Sampakning(String navn, int pant, String æsketype, int antalOel, int antalGlas, int størrelse){
        super(navn, pant, Varetype.SAMPAKNING);
        this.æsketype = æsketype;
        this.antalOel = antalOel;
        this.antalGlas = antalGlas;
        if(størrelse > 12) drikkevarer = new Drikkevare[12];
        else if (størrelse < 2) drikkevarer = new Drikkevare[2];
        else drikkevarer = new Drikkevare[størrelse];
        antalFyldt = 0;
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

    public ArrayList<Drikkevare> getDrikkevarer(){
        return new ArrayList<>(drikkevarer);
    }

    public void removeDrikkevare(Drikkevare drikkevare){
        if(drikkevarer[0] != null){
            drikkevarer[antalFyldt] = null;
            antalFyldt--;
        }
    }

    public void addDrikkevare(Drikkevare drikkevare){
        if(drikkevarer.length > antalFyldt){
            drikkevarer[antalFyldt] = drikkevare;
            antalFyldt++;
        }
    }


}
