package Application.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Vare implements Serializable {
    private String navn;
    private int pant;
    private Varetype varetype;
    private ArrayList<Prisgruppe>prisgrupper = new ArrayList<>();
    private String aktivPrisgruppe;

    Vare(String navn, int pant, Varetype type) {
        this.navn = navn;
        this.pant = pant;
        this.varetype = type;
        aktivPrisgruppe = null;
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

    public double getPris(String pgNavn){
        Prisgruppe retPg = null;
        for(Prisgruppe pg : prisgrupper){
            if(pg.getNavn().equalsIgnoreCase(pgNavn)){
                retPg = pg;
            }
        }
        return retPg.getPris();
    }

    public void removePrisgruppe(Prisgruppe prisgruppe){
        if(!prisgrupper.contains(prisgruppe)){
            prisgrupper.remove(prisgruppe);
        }
    }

    public void setAktivPrisgruppe(String pgNavn){
        this.aktivPrisgruppe = pgNavn;
    }

    public String getAktivPrisgruppe(){
        return aktivPrisgruppe != null ? aktivPrisgruppe : "Ingen prisgruppe valgt";
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

    // Author: Jens Mossen
    @Override
    public String toString(){
        String retStr = null;
        try{
            Prisgruppe aktivPg = null;
            for(Prisgruppe pg : prisgrupper){
                if(pg.getNavn().equalsIgnoreCase(aktivPrisgruppe)){
                    aktivPg = pg;
                }
            }
            retStr = this.getNavn() + " " + aktivPg.getPris();
        }catch(NullPointerException ex){
            retStr = this.getNavn() + "  NaN";
        }
        return retStr;
    }
}
