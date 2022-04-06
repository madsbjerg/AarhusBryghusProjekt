package Application.Models;

import java.io.Serializable;

public class ProcentRabat implements Rabat, Serializable {

    private double procentSats;

    public ProcentRabat(double procentSats) {
        if(procentSats <0.1 || procentSats > 100){
            throw new IllegalArgumentException("Værdien skal være mellem 0 og 100");
        }
        this.procentSats = procentSats;
    }

    @Override
    public double beregnRabat(double sum) {
        return sum * (1-(procentSats/100));
    }
    public double getProcentSats() {
        return procentSats;
    }
    @Override
    public String toString(){
        return "Procent Rabat" + " Sats:" + getProcentSats();
    }
}
//