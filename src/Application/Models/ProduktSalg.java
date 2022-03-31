package Application.Models;

import java.util.HashMap;

public class ProduktSalg extends Salg{
    private double Beloeb;
    ProduktSalg(HashMap<Vare, Integer> varer, double beloeb) {
        super(varer);
        this.Beloeb = beloeb;
    }
/*
    public double totalPris(int prisgruppe){
        double sum = 0;
        for(Vare vare : varer.keySet()){
            sum += vare.getPris(prisgruppe) * (int)varer.get(vare);
        }
        this.Beloeb = sum;
        return sum;
    }
*/
    public double getBeloeb() {
        try {
            return Beloeb;
        }
        catch (NullPointerException e){
            System.out.println("Du har ikke udregnet beløbet endnu");

            System.out.println(e.getMessage());
    }
        //Der skal være en return statement
        //Den returnere -1, hvis beloeb ikke er blevet beregnet.
        return -1;
    }
}
