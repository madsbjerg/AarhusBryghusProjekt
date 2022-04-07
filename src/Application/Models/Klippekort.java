package Application.Models;

import java.util.InputMismatchException;

public class Klippekort extends Vare{
    private double antalKlip =4;
    private String navnKunde;

    public Klippekort( String navnKunde) {
        super("navn",0,Varetype.KLIPPEKORT);
        this.navnKunde = navnKunde;
    }

    public Klippekort(){
        super("",0,Varetype.KLIPPEKORT);
    }

    /**
     * Laver en variabel, for ikke at manipulere med dataen.
     * Tjekker om der er nok klip på kortet, hvis nej kaster metoden en illegalArguementException.
     * @param klipPris = hvor mange klip der skal bruges
     */
    public void brugKlip(double klipPris){
        if(klipPris <=0){
            throw new IllegalArgumentException("klipPris skal være 1 eller højere.");
        }
        double antalKlipTilbage = antalKlip;
        if(!((antalKlipTilbage -klipPris) < 0)){
            antalKlip = antalKlip - klipPris;
        } else {
            throw new IllegalArgumentException("Du forsøger at bruge flere klip end der er på kortet");
        }
    }
    public double getAntalKlip(){
        return antalKlip;
    }

    public String getNavnKunde(){
        return navnKunde;
    }
    public String toString (){
        String returnString;
        returnString = navnKunde + " \nAntal klip tilbage: " + getAntalKlip();
        return returnString;
    }


}
