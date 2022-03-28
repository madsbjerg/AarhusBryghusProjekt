package Application.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Salg {
    // Rabat rabat = null;
    private double totalBeloeb;
    Map<Vare, Integer> varer;

    Salg(HashMap<Vare, Integer> varer){
        this.varer = varer;
    }

    public double beregnTotal(int prisgruppe){
        double sum = 0;
        for(Vare vare : varer.keySet()){
            sum += vare.getPris(prisgruppe) * (int)varer.get(vare);
        }
        this.totalBeloeb = sum;
        return sum;
    }

    public double getTotalBeloeb(){
        return this.totalBeloeb;
    }

}
