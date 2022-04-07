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

    public double totalPris(String pgnavn){
        double sum = 0;
        for(Vare vare : varer.keySet()){
            sum += vare.getPris(pgnavn) * varer.get(vare);
        }
        this.Beloeb = sum;
        return sum;
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

    @Override
    public String toString(){
        String navn =  "Salg lavet den. " + salgsDato +
                "\n"
                + varer;
        if(rabat != null){
            navn +="\n" +rabat;
        }
        navn += "\n" + "Total pris: " + Beloeb;
        if(rabat != null){
            navn += "\n"+ "Med rabat: " + rabat.beregnRabat(Beloeb);
        }
        navn +="\n" + "Betalt med: " + betalingsform +"\n";
        return navn;
    }
}