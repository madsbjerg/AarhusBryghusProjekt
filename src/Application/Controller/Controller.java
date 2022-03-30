package Application.Controller;

import Application.Models.*;
import Storage.Storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import Storage.Storage;

import java.util.ArrayList;

public class Controller {
    private static Controller controller;

    public static Controller getController(){
        if(controller == null) controller = new Controller();
        return controller;
    }

    public static Drikkevare createFadøl(String navn, int pant,double alkoholProcent){
        Drikkevare d1 = new Drikkevare(navn, pant, Varetype.FADØL, alkoholProcent);
        Storage.getStorage().addVare(d1);
        return d1;
    }

    public static Drikkevare createFlaske(String navn, int pant, double alkoholProcent){
        Drikkevare d2 = new Drikkevare(navn, pant, Varetype.FLASKE,alkoholProcent);
        Storage.getStorage().addVare(d2);
        return d2;
    }

    public static Drikkevare createSpiritus(String navn, int pant, double alkoholProcent){
        Drikkevare d3 = new Drikkevare(navn, pant, Varetype.SPIRITUS,alkoholProcent);
        Storage.getStorage().addVare(d3);
        return d3;
    }

    public static Rundvisning createRundvisning(String navn,  Varetype type, int antalPersoner, LocalDateTime tidspunkt){
        return new Rundvisning(navn, Varetype.RUNDVISNING, antalPersoner, tidspunkt);
    }

    public static Diverse createBeklædning(String navn, int pant, String beskrivelse){
        return new Diverse(navn, pant, Varetype.BEKLÆDNING, beskrivelse);
    }

    public static Diverse createMalt(String navn, int pant, Varetype type, String beskrivelse){
        return new Diverse(navn, pant, Varetype.MALT, beskrivelse);

    }

    public static Diverse createGlas(String navn, int pant,Varetype varetype, String beskrivelse){
        return new Diverse(navn, pant, Varetype.GLAS, beskrivelse);
    }


    public static void initStorage(){

        Klippekort k1 = new Klippekort(1, "Olaf");
        Klippekort k2 = new Klippekort(2, "Mads");
        Klippekort k3 = new Klippekort(3, "Jens");
        Klippekort k4 = new Klippekort(4, "Mike");

        Sampakning s1 = new Sampakning("Gaveæske", 0, "Gaveæske", 1, 1, 20);
        Sampakning s2 = new Sampakning("trækasse", 0, "Trækasse", 4, 3, 25);
        Sampakning s3 = new Sampakning("Gavekurv", 0, "Gavekurv", 2, 4, 30);

        Vare bajer = createFadøl("Bajer", 0, 4);
        Vare tøj = createBeklædning("Høj", 0, "høj");


    }

    public ArrayList<Vare> getKlippekort(){
        Storage s = Storage.getStorage();
        ArrayList<Vare> rl = new ArrayList<>();
        for(Vare v : s.getVarer()){
            if(v.getVaretype() == Varetype.KLIPPEKORT){
                rl.add(v);
            }
        }
        return rl;
    }

    public ArrayList<Salg> getUdlejninger(){
        Storage s = Storage.getStorage();
        ArrayList<Salg> rl = new ArrayList<>();
        for(Salg sa : s.getSalg()){
            if(sa instanceof Udlejning){
                rl.add(sa);
            }
        }
        return rl;
    }

    public ArrayList<Vare> getRundvisninger(){
        Storage s = Storage.getStorage();
        ArrayList<Vare> rl = new ArrayList<>();
        for(Vare v : s.getVarer()){
            if(v.getVaretype() == Varetype.RUNDVISNING){
                rl.add(v);
            }
        }
        return rl;
    }

    public ArrayList<Vare> getVarer(){
        return Storage.getStorage().getVarer();
    }

    public ArrayList<Prisgruppe> getPrisgrupper(){
        ArrayList<Prisgruppe> prisgrupper = new ArrayList<>();
        for(Vare v : Storage.getStorage().getVarer()){
            for(Prisgruppe pg : v.getPrisgrupper()){
                if(!prisgrupper.contains(pg)){
                    prisgrupper.add(pg);
                }
            }
        }
        return prisgrupper;
    }

}
