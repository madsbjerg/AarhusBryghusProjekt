package Application.Models;

import java.util.HashMap;

public class ProduktSalg extends Salg {
    private double Beloeb;

    public ProduktSalg(HashMap<Vare, Integer> varer, double beloeb, Betalingsform betalingsform, Rabat rabat) {
        super(varer, betalingsform, rabat);
        if(betalingsform == null){
            throw new IllegalArgumentException("Du skal vælge betalingsform");
        }
        if(varer == null){
            throw new IllegalArgumentException("Du skal have vare, før du kan lave et salg");
        }
        if(beloeb < 0){
            throw new IllegalArgumentException("Beløbet skal være over 0");
        }
        this.Beloeb = beloeb;
    }


    public double getBeloeb() {
        return Beloeb;
    }

    public Betalingsform getBetalingsform (){
        return betalingsform;
    }

    public HashMap<Vare,Integer> getVare(){
        return varer;
    }

    public Rabat getRabat(){
        return rabat;
    }
}