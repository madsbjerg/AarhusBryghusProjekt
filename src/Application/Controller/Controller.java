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
        Drikkevare d3 = new Drikkevare(navn, 0, Varetype.SPIRITUS,alkoholProcent);
        Storage.getStorage().addVare(d3);
        return d3;
    }

    public static Rundvisning createRundvisning(String navn, int antalPersoner, LocalDateTime tidspunkt){
        return new Rundvisning(navn, Varetype.RUNDVISNING, antalPersoner, tidspunkt);
    }

    public static Diverse createBeklædning(String navn, String beskrivelse){
        return new Diverse(navn, 0, Varetype.BEKLÆDNING, beskrivelse);
    }

    public static Diverse createMalt(String navn, String beskrivelse){
        return new Diverse(navn, 0, Varetype.MALT, beskrivelse);

    }

    public static Diverse createGlas(String navn, String beskrivelse){
        return new Diverse(navn, 0, Varetype.GLAS, beskrivelse);
    }


    public static void initStorage(){

        Klippekort k1 = new Klippekort(1, "Olaf");
        Klippekort k2 = new Klippekort(2, "Mads");
        Klippekort k3 = new Klippekort(3, "Jens");
        Klippekort k4 = new Klippekort(4, "Mike");

        Sampakning s1 = new Sampakning("Gaveæske", 0, "Gaveæske", 1, 1, 20);
        Sampakning s2 = new Sampakning("trækasse", 0, "Trækasse", 4, 3, 25);
        Sampakning s3 = new Sampakning("Gavekurv", 0, "Gavekurv", 2, 4, 30);


        // ---- Opret fadøl objekter ----------------------------
        Controller.createFadøl("Klosterbryg", 0, 8.00);
        Controller.createFadøl("Jazz Classic", 0, 6.00);
        Controller.createFadøl("Extra Pilsner", 0, 6.40);
        Controller.createFadøl("Celebration", 0, 5.20);
        Controller.createFadøl("Blondie", 0, 5.00);
        Controller.createFadøl("Forårsbryg", 0, 5.50);
        Controller.createFadøl("India Pale Ale", 0, 7.00);
        Controller.createFadøl("Julebryg", 0, 6.4);
        Controller.createFadøl("Imperial Stout", 0, 9.00);
        Controller.createFadøl("Special", 0, 7.50);

        //---- Opret flaske objekter -----------------------------
        Controller.createFlaske("Klosterbryg", 0, 6.40);
        Controller.createFlaske("Sweet Georgia Brown", 0, 7.50);
        Controller.createFlaske("Extra Pilsner", 0, 8.00);
        Controller.createFlaske("Celebration", 0, 6.40);
        Controller.createFlaske("Blondie", 0, 4.80);
        Controller.createFlaske("Forårsbryg", 0, 5.50);
        Controller.createFlaske("India Pale Ale", 0, 6.40);
        Controller.createFlaske("Julebryg", 0, 7.20);
        Controller.createFlaske("Juletønden", 0, 7.20);
        Controller.createFlaske("Old Strong Ale", 0, 9.5);
        Controller.createFlaske("Fregatten Jylland", 0, 6.40);
        Controller.createFlaske("Imperial Stout", 0, 7.40);
        Controller.createFlaske("Tribute", 0, 6.40);
        Controller.createFlaske("Black Monster", 0, 8.40);

        //---- Opret spiritus objekter ---------------------------
        Controller.createSpiritus("Whisky 45% 50cl rør", 0, 45.00);
        Controller.createSpiritus("Whisky 45% 4 cl", 0, 45.00);
        Controller.createSpiritus("Whisky 43% 50cl rør", 0, 43.00);
        Controller.createSpiritus("Whisky med egesplint", 0, 45.00);
        Controller.createSpiritus("Whisky uden egesplint", 0, 45.00);
        Controller.createSpiritus("Liquor of Aarhus", 0, 45.00);
        Controller.createSpiritus("Lyng gin 50 cl", 0, 45.00);
        Controller.createSpiritus("Lyng gin 4 cl", 0, 45.00);

        //---- Opret fustage objekter ---------------------------
        Controller.createFustage("Klosterbryg", 200, 6.40, false);
        Controller.createFustage("Jazz Classic", 200, 6.40, false);
        Controller.createFustage("Extra Pilsner", 200, 6.40, false);
        Controller.createFustage("Celebration", 200, 6.40, false);
        Controller.createFustage("Blondie", 200, 6.40, false);
        Controller.createFustage("Forårsbryg", 200, 6.40, false);
        Controller.createFustage("India Pale Ale", 200, 6.40, false);
        Controller.createFustage("Julebryg", 200, 6.40, false);
        Controller.createFustage("Imperial Stout", 200, 6.40, false);

        //---- Opret rundvisning objekter -----------------------

        Controller.createRundvisning("Carlsberg spionage", 5, LocalDateTime.of(2022, 4, 8, 12, 30));

        //---- Opret beklædning objekter ------------------------

        Controller.createBeklædning("T-shirt", "T-shirt");
        Controller.createBeklædning("Polo", "Polo");
        Controller.createBeklædning("Cap", "Cap");


        //---- Opret malt objekter ------------------------------

        Controller.createMalt("Malt 25 kg", "Malt 25 kg");


        //---- Opret glas objekter ------------------------------

        Controller.createGlas("Ølglas", "Ølglas");
        Controller.createGlas("2 Whiskyglas + brikker", "2 Whiskyglas + brikker");
        Controller.createGlas("Krus");

        

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

}
