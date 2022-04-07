package Application.Models;

import java.util.HashMap;

public class Regning extends Salg{
    private double beloebTotal;
    private String navnKunde;
    private boolean betalt = false;
    public Regning(HashMap<Vare, Integer> varer, Betalingsform betalingsform, Rabat rabat, double beloebTotal, String navnKunde) {
        super(varer, betalingsform, rabat);
        this.beloebTotal = beloebTotal;
        this.navnKunde = navnKunde;
    }


    public double getBeloebTotal() {
        return beloebTotal;
    }

    public String getNavnKunde() {
        return navnKunde;
    }
    public void setBetalt(boolean betalt) {
        this.betalt = betalt;
    }
    public boolean isBetalt() {
        return betalt;
    }

    @Override
    public String toString(){
        String navn = "Regning lavet den. " + salgsDato + "\n" + "Navn på kunde: " + navnKunde
                + "\n" + varer;
        if(rabat != null){
            navn += "\n" + rabat;
        }
        navn +="\n" + "Total pris " +beloebTotal;
        if(rabat != null){
            navn += "\n" + "Med rabat: " + rabat.beregnRabat(beloebTotal);
        }

        if(isBetalt()){
            navn += "\n" + "Varen er betalt";
            navn += "\n" + "Betalt med: " + betalingsform;
        } else {
            navn += "\n" + "Ikke betalt" +"\n";
        }

        return navn;
    }



}
