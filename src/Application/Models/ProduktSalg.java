package Application.Models;

import java.util.HashMap;

public class ProduktSalg extends Salg{
    private double Beloeb;
    public ProduktSalg(HashMap<Vare, Integer> varer, double beloeb, Betalingsform betalingsform,Rabat rabat) {
        super(varer,betalingsform,rabat);
        this.Beloeb = beloeb;
    }

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
