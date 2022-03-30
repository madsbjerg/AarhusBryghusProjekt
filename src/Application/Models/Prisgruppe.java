package Application.Models;

public class Prisgruppe {


    // ----- Basics-------
    private double pris;
    private String navn;

    public Prisgruppe(double pris, String navn) {
        this.pris = pris;
        this.navn = navn;
    }

    public double getPris() {
        return pris;
    }

    public String getNavn() {
        return navn;
    }

    // --------------------

}
