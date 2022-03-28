package Application.Models;

import java.time.LocalDateTime;

public class Rundvisning extends Vare{
    private int antalPersoner;
    private LocalDateTime tidspunkt;

    Rundvisning(String navn, int pant, Varetype type, int antalPersoner, LocalDateTime tidspunkt){
        super(navn, pant, Varetype.RUNDVISNING);
        this.antalPersoner = antalPersoner;
        this.tidspunkt = tidspunkt;
    }

    public int getAntalPersoner(){
        return antalPersoner;
    }

    public LocalDateTime getTidspunkt(){
        return tidspunkt;
    }
}
