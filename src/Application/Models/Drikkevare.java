package Application.Models;

public class Drikkevare extends Vare {
    private double alkoholProcent;
 
    public Drikkevare(String navn, int pant, Varetype varetype, double alkoholProcent){
        super(navn, pant, varetype);
        this.alkoholProcent = alkoholProcent;
    }

    public void createFadøl(String navn, int pant, double alkoholProcent){
        new Drikkevare(navn, pant, Varetype.FADØL, alkoholProcent);
    }

    public void createFlaske(String navn, int pant, double alkoholProcent){
        new Drikkevare(navn, pant, Varetype.FLASKE,alkoholProcent);
    }

    public void createSpiritus(String navn, int pant, double alkoholProcent){
        new Drikkevare(navn, pant, Varetype.SPIRITUS,alkoholProcent);
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    } 

}
