package Application.Models;

public class ProcentRabat implements Rabat {
    private double procentSats;

    public ProcentRabat(double procentSats) {
        this.procentSats = procentSats;
    }

    @Override
    public double beregnRabat(double sum) {
        return sum * (1+procentSats);
    }
}
