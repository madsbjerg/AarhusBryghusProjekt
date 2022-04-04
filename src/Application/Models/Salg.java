package Application.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Salg implements Serializable {
    Rabat rabat = null;
    Betalingsform betalingsform;
    HashMap<Vare, Integer> varer;

    Salg(HashMap<Vare, Integer> varer, Betalingsform betalingsform, Rabat rabat){
        this.varer = varer; // lmao
        this.betalingsform = betalingsform;
        this.rabat = rabat;

    }

    public double totalPris(int prisgruppe){
      throw new  UnsupportedOperationException();
    }
}
