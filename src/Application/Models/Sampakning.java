package Application.Models;
public class Sampakning extends Vare{
    private int antalOel;
    private int antalGlas;
    private Drikkevare[] drikkevarer;
    private int antalFyldt;                    // Hjælpe attribut til array.

    //private final ArrayList<Drikkevare> drikkevarer = new ArrayList<>();


    public Sampakning(String navn,int antalOel, int antalGlas){
        super(navn, 0, Varetype.SAMPAKNING);
        this.antalOel = antalOel;
        this.antalGlas = antalGlas;
        if(antalOel > 12) drikkevarer = new Drikkevare[12];
        else if (antalOel < 2) drikkevarer = new Drikkevare[2];
        else drikkevarer = new Drikkevare[antalOel];
        antalFyldt = 0;
    }

    public int getAntalOel() {
        return antalOel;
    }

    public int getAntalGlas() {
        return antalGlas;
    }

    public Drikkevare[] getDrikkevarer(){
        return drikkevarer;
    }

    public void removeDrikkevare(Drikkevare drikkevare){
        if(drikkevarer[0] != null){
            drikkevarer[antalFyldt] = null;
            antalFyldt--;
        }
    }

    public void moveAllDrikkevare(){
    for(int i =0;i< drikkevarer.length;i++){
        drikkevarer[i] = null;
        antalFyldt =0;
        }
    }

    public void addDrikkevare(Drikkevare drikkevare){
        if(drikkevarer.length > antalFyldt){
            drikkevarer[antalFyldt] = drikkevare;
            antalFyldt++;
        } else {
            throw new IllegalArgumentException("Du prøver at fylde flere drikkevare i sampakningen, end der er plads til");
        }
    }
}
