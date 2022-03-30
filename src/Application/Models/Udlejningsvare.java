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

}
