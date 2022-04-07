package Application.Models;

import java.time.LocalDateTime;

public class Rundvisning extends Vare{
    private int antalPersoner;
    private LocalDateTime tidspunkt;

    public Rundvisning(String navn, int antalPersoner, LocalDateTime tidspunkt){
        super(navn, 0, Varetype.RUNDVISNING);
        this.antalPersoner = antalPersoner;
        this.tidspunkt = tidspunkt;   
    }

    public int getAntalPersoner(){
        return antalPersoner;
    }

    public LocalDateTime getTidspunkt(){
        return tidspunkt;
    }
    @Override
    public String toString(){
        return getNavn()+" " + tidspunkt;
    }
}
