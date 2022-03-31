package Application.Models;

import java.util.InputMismatchException;

public class Klippekort extends Vare{
    private int antalKlip =4;
    private String navnKunde;

    public Klippekort( String navnKunde) {
        super("navn",0,Varetype.KLIPPEKORT);
        this.navnKunde = navnKunde;
    }

    public void brugKlip(){
        if(antalKlip >0) {
            antalKlip--;
        }
        else {
            throw new InputMismatchException();
        }
    }
    public int getAntalKlip(){
        return antalKlip;
    }


}
