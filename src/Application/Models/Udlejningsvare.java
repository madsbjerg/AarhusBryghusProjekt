package Application.Models;

public class Udlejningsvare extends Vare{

    private boolean ude;

    public Udlejningsvare(String navn, int pant, Varetype type, boolean ude) {
        super(navn, pant, type);
        this.ude = ude;
    }

    public boolean isUde() {
        return ude;
    }

    public void setUde(boolean ude) {
        this.ude = ude;
    }

    public Udlejningsvare createFustage (String navn, int pant, boolean ude){
        return new Udlejningsvare(navn, pant, Varetype.FUSTAGE, ude);
    }

    public Udlejningsvare createAnlaeg(String navn, int pant, boolean ude){
        return new Udlejningsvare(navn, pant, Varetype.ANLÆG, ude);
    }

    public Udlejningsvare createKulsyre(String navn, int pant, boolean ude){
        return new Udlejningsvare(navn, pant, Varetype.KULSYRE, ude);
    }

}
