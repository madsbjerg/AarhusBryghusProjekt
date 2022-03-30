package Application.Models;

import java.util.InputMismatchException;

public class Klippekort {
    private int antalKlip =4;
    private String navnKunde;

    public Klippekort(int antalKlip, String navnKunde) {
        this.antalKlip = antalKlip;
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
