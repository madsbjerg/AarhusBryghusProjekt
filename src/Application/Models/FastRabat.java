package Application.Models;

public class FastRabat implements Rabat {
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