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
    private HashMap<Vare, Integer> returVarer;

    public Udlejning(HashMap<Vare, Integer> varer, double pantBeloeb, LocalDate startDato, LocalDate slutDato,
                     Betalingsform betalingsform, Rabat rabat){
        super(varer,betalingsform, rabat);
        if(varer == null){
            throw new IllegalArgumentException("Tilføj vare til udlejningen");
        }
        if(betalingsform == null){
            throw new IllegalArgumentException("Tilføj betalingsform til udlejning");
        }
        if(startDato.isAfter(slutDato)){
            throw new IllegalArgumentException("Start datoen skal være før slut datoen");
        }
        this.pantBeloeb = pantBeloeb;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.betalt = false;
        returVarer = new HashMap<>();
    }

    public double getPantBeloeb(){
        return this.pantBeloeb;
    }

    public double getTotalBeloeb(){
        return this.totalBeloeb;
    }

    public boolean isBetalt(){
        return this.betalt;
    }

    public void setBetalt(boolean betalt){
        this.betalt = betalt;
    }

    public void setReturVarer(HashMap<Vare, Integer> retur){
        this.returVarer = retur;
    }

    @Override
    public String toString(){
        return "" + slutDato;
    }

}
