package Storage;

import Application.Models.Salg;
import Application.Models.Vare;

import java.io.Serializable;
import java.util.ArrayList;

public class Storage implements Serializable {
    private static Storage storage;
    private ArrayList<Vare> varer = new ArrayList<>();
    private ArrayList<Salg> salg = new ArrayList<>();

    public Storage getStorage(){
        if(storage == null) storage = new Storage();
        return storage;
    }

    public ArrayList<Vare> getVarer(){
        return varer;
    }

    public ArrayList<Salg> getSalg(){
        return salg;
    }

    public void addSalg(Salg s){
        if(!this.salg.contains(s)) this.salg.add(s);
    }

    public void addVare(Vare v){
        if(!this.varer.contains(v)) this.varer.add(v);
    }
}
