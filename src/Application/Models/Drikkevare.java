package Application.Models;

public class Drikkevare extends Vare {
    private double alkoholProcent;
 
    public Drikkevare(String navn, int pant, Varetype varetype, double alkoholProcent){
        super(navn, pant, varetype);
        this.alkoholProcent = alkoholProcent;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    } 

    @Override
    public String toString(){
        return this.getNavn();
    }
}
