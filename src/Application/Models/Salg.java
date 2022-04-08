package Application.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Salg implements Serializable {
    Rabat rabat = null;
    Betalingsform betalingsform;
    HashMap<Vare, Integer> varer;
    LocalDate salgsDato;

    Salg(HashMap<Vare, Integer> varer, Betalingsform betalingsform, Rabat rabat) {
        this.varer = varer;
        this.betalingsform = betalingsform;
        this.rabat = rabat;
        salgsDato = LocalDate.now();

    }

    public Rabat getRabat() {
        return this.rabat;
    }

    public HashMap<Vare, Integer> getVarer() {
        return this.varer;
    }

    public LocalDate getSalgsDato(){
        return salgsDato;
    }
}
