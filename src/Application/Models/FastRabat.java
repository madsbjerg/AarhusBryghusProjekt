package Application.Models;

import java.io.Serializable;

public class FastRabat implements Rabat, Serializable {
    private double beloeb;


    public FastRabat(double beloeb) {
        this.beloeb = beloeb;
    }

    @Override
    public double beregnRabat(double sum) {
        return sum - beloeb;
    }
}
//