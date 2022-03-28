package Application.Models;

import java.util.HashMap;
import java.util.Map;

public abstract class Vare {
    // <prisgruppe, aktuel pris>
    private Map priser = new HashMap<Integer, Integer>();
    private Varetype varetype;

    public int getPris(int gruppe){
        return (int)priser.get(gruppe);
    }

    public void addPris(int gruppe, int pris){
        if(!priser.containsKey(gruppe)){
            priser.put(gruppe, pris);
        }
    }

    public Varetype getVaretype(){
        return varetype;
    }

    public void setVaretype(Varetype type){
        varetype = type;
    }
}
