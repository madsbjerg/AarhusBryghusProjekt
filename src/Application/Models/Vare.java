package Application.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Vare {
    private String navn;
    private int pant;
    private Varetype varetype;
    private ArrayList<Prisgruppe>prisgrupper = new ArrayList<>();

    Vare(String navn, int pant, Varetype type) {
        this.navn = navn;
        this.pant = pant;
        this.varetype = type;
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

    public Prisgruppe getPris(String pgNavn){
        Prisgruppe retPg = null;
        for(Prisgruppe pg : prisgrupper){
            if(pg.getNavn().equalsIgnoreCase(pgNavn)){
                retPg = pg;
            }
        }
        return retPg;
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
