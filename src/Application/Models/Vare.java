package Application.Models;

import java.util.HashMap;
import java.util.Map;

public abstract class Vare {
    // <prisgruppe, aktuel pris>
    private Map priser = new HashMap<Integer, Integer>();
    private Varetype varetype;



}
