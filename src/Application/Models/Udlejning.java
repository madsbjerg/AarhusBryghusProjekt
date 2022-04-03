package Application.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Udlejning extends Salg{

    private double pantBeloeb;
    private double totalBeloeb;
    private LocalDate startDato;
    private LocalDate slutDato;
    private boolean betalt;

    public Udlejning(HashMap<Vare, Integer> varer, double pantBeloeb, LocalDate startDato, LocalDate slutDato,
                     Betalingsform betalingsform, Rabat rabat){
        super(varer,betalingsform, rabat);
        this.pantBeloeb = pantBeloeb;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.betalt = false;
    }
    public double totalPris(String pgnavn){
        double sum =0;
        for(Vare vare : varer.keySet()){
            sum += vare.getPris(pgnavn)*varer.get(vare);
        }
        this.totalBeloeb = sum;
        return sum - pantBeloeb;
    }

    public void udregnPant (){



    }



}
