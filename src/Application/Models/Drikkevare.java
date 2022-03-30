package Application.Models;

public class Drikkevare extends Vare {
    private double alkoholProcent;
 
    private Drikkevare(String navn, int pant, Varetype varetype, double alkoholProcent){
        super(navn, pant, varetype);
        this.alkoholProcent = alkoholProcent;
    }

    public Drikkevare createFadøl(String navn, int pant, double alkoholProcent){
        return new Drikkevare(navn, pant, Varetype.FADØL, alkoholProcent);
    }

    public Drikkevare createFlaske(String navn, int pant, double alkoholProcent){
        return new Drikkevare(navn, pant, Varetype.FLASKE,alkoholProcent);
    }

    public Drikkevare createSpiritus(String navn, int pant, double alkoholProcent){
        return new Drikkevare(navn, pant, Varetype.SPIRITUS,alkoholProcent);
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    } 

}
