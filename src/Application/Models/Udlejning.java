package Application.Models;

import java.time.LocalDate;
import java.util.HashMap;

public class Udlejning extends Salg{
    private double pantBeloeb;
    private double totalBeloeb;
    private LocalDate startDato;
    private LocalDate slutDato;
    private boolean betalt;
    private List<Udlejningsvare> udlejningsvare;

    Udlejning(HashMap<Vare, Integer> varer, ArrayList<Udlejningsvare> udlejningsvare, LocalDate startDato, LocalDate slutDato){
        super(varer);
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.betalt = false;
        for(Udlejningsvare vare : udlejningsvare){
            
        }
    }



}
