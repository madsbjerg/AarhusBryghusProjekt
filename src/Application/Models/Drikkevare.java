package Application.Models;

public class Drikkevare extends Vare {
    private double alkoholProcent;
 
    public Drikkevare(String navn, int pant, Varetype varetype, double alkoholProcent){
        super(navn, pant, varetype);
        if(alkoholProcent <0 || alkoholProcent >99){
                throw new IllegalArgumentException ("Alkohol-procenten skal være mellem 0 og 99");
        }
        this.alkoholProcent = alkoholProcent;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    } 

}
