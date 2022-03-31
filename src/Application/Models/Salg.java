package Application.Models;

import java.util.HashMap;
import java.util.Map;

public abstract class Salg {
    Rabat rabat = null;
    Betalingsform betalingsform;
    Map<Vare, Integer> varer;

    Salg(HashMap<Vare, Integer> varer, Betalingsform betalingsform, Rabat rabat){
        this.varer = varer; // lmao
        this.betalingsform = betalingsform;
        this.rabat = rabat;

    }

    public double totalPris(int prisgruppe){
      throw new  UnsupportedOperationException();
    }


}
