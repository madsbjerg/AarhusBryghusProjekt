package Application.Models;

public class Udlejningsvare extends Vare{

    public Udlejningsvare(String navn, int pant, Varetype type) {
        super(navn, pant, type);
    }

    @Override
    public String toString(){
        return this.getNavn() + " Pant: " + this.getPant() + " Pris: " + this.getPris("Butik");
    }
    public String toString(String pgNavn){
        return this.getNavn() + " Pant: " + this.getPant() + " Pris: " + this.getPris(pgNavn);
    }
}
