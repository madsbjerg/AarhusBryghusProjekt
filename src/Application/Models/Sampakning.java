package Application.Models;

import java.util.ArrayList;

public class Sampakning extends Vare{

    private String æsketype;
    private int antalOel; 
    private int antalGlas;

    private final ArrayList<Drikkevare> drikkevarer = new ArrayList<>();


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

    public ArrayList<Drikkevare> getDrikkevarer(){
        return new ArrayList<>(drikkevarer);
    }

    public void removeDrikkevare(Drikkevare drikkevare){
        if(drikkevarer.contains(drikkevare)){
            drikkevarer.remove(drikkevare);
        }
    }

    public void addDrikkevare(Drikkevare drikkevare){
        if(!drikkevarer.contains(drikkevare)){
            drikkevarer.add(drikkevare);
        }
    }


}
