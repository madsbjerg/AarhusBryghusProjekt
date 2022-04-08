package Application.Models;

import java.io.Serializable;

public class FastRabat implements Rabat, Serializable {
    private double beloeb;

    public FastRabat(double beloeb) {
        this.beloeb = beloeb;
        if(beloeb < 0){
            throw new IllegalArgumentException("Rabat skal være over 0");
        }
    }

    @Override
    public double beregnRabat(double sum) {
        return sum - beloeb;   
    }

    @Override
    public String toString(){
        return "Fast Rabat: " + beloeb;
    }
}
