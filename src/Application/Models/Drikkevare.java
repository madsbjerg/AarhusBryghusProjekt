package Application.Models;

public class Drikkevare extends Vare {
    private double alkoholProcent;
 
    private Drikkevare(String navn, int pant, Varetype varetype, double alkoholProcent){
        super(navn, pant, varetype);
        this.alkoholProcent = alkoholProcent;
    }

    public void createFadøl(String navn,  double alkoholProcent){
        new Drikkevare(navn, 0,Varetype.FADØL, alkoholProcent);
    }

    public void createFlaske(String navn, double alkoholProcent){
        new Drikkevare(navn, 0, Varetype.FLASKE,alkoholProcent);
    }

    public void createSpiritus(String navn, double alkoholProcent){
        new Drikkevare(navn, 0, Varetype.SPIRITUS,alkoholProcent);
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }
}
