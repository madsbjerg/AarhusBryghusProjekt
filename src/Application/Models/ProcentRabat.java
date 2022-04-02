package Application.Models;

import java.io.Serializable;

public class ProcentRabat implements Rabat, Serializable {
    private double procentSats;

    public ProcentRabat(double procentSats) {
        this.procentSats = procentSats;
    }

    @Override
    public double beregnRabat(double sum) {
        return sum * (1-(procentSats/100));
    }
}
//