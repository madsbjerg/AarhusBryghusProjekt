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

    Udlejning(HashMap<Vare, Integer> varer, ArrayList<Udlejningsvare> udlejningsvare, LocalDate startDato, LocalDate slutDato){
        super(varer);
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.betalt = false;
    }






}
