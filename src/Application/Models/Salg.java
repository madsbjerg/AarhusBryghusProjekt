package Application.Models;

import java.util.HashMap;
import java.util.Map;

public abstract class Salg {
    Rabat rabat = null;
    Betalingsform betalingsform;
    Map<Vare, Integer> varer;

    Salg(HashMap<Vare, Integer> varer){
        this.varer = varer; // lmao
    }

    public double totalPris(int prisgruppe){
      throw new  UnsupportedOperationException();
    }


}
