package Application.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Salg {
    Rabat rabat = null;

    Map<Vare, Integer> varer;

    Salg(HashMap<Vare, Integer> varer){
        this.varer = varer;
    }

    public double beregnTotal(int prisgruppe){
      throw new  UnsupportedOperationException();
    }


}
