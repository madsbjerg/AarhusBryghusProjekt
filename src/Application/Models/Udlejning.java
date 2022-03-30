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


    public void initPant(){
        // for fustage initPant 200 * Fustager.
        // for kulsyre initPant 1000 * kulsyre.

        double pant = 0;
        if(Varetype.FUSTAGE != null){
            for (Varetype.FUSTAGE fustage : fustager){
                pant += 200;
            }
        }
        if(Varetype.KULSYRE != null){
            for (Varetype.KULSYRE kulsyre : kulsyrer){
                pant += 1000;
            }
        }
    }



}
