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



}
