package Application.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Vare {
    private String navn;
    // <prisgruppe, aktuel pris>
    private Map priser = new HashMap<Integer, Integer>();
    private int pant;
    private Varetype varetype;
    private ArrayList<Prisgruppe>prisgrupper = new ArrayList<>();

    Vare(String navn, int pant, Varetype type) {
        this.navn = navn;
        this.pant = pant;
        this.varetype = type;
    }

    public int getPris(int gruppe){
        return (int)priser.get(gruppe);
    }



    public void addPris(int gruppe, int pris){
        if(!priser.containsKey(gruppe)){
            priser.put(gruppe, pris);
        }
    }


    // ------ Link metoder til prisgruppe----------

    public ArrayList<Prisgruppe> getPrisgrupper() {
        return prisgrupper;
    }
    public void addPrisgruppe(Prisgruppe prisgruppe){
        if(!prisgrupper.contains(prisgruppe)){
            prisgrupper.add(prisgruppe);
        }
    }
    public void removePrisgruppe(Prisgruppe prisgruppe){
        if(!prisgrupper.contains(prisgruppe)){
            prisgrupper.remove(prisgruppe);
        }
    }
// ------------------------------------------------------


    public Varetype getVaretype(){
        return varetype;
    }

    public void setVaretype(Varetype type){
        varetype = type;
    }

    public String getNavn(){
        return navn;
    }

    public int getPant(){
        return pant;
    }

    public void setPant(int pant){
        this.pant = pant;
    }
}
