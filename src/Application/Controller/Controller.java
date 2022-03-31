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

    public static Drikkevare createFadøl(String navn,double alkoholProcent){
        Drikkevare d1 = new Drikkevare(navn, 0, Varetype.FADØL, alkoholProcent);
        Storage.getStorage().addVare(d1);
        return d1;
    }

    public static Drikkevare createFlaske(String navn, double alkoholProcent){
        Drikkevare d2 = new Drikkevare(navn, 0, Varetype.FLASKE,alkoholProcent);
        Storage.getStorage().addVare(d2);
        return d2;
    }

    public static Drikkevare createSpiritus(String navn, double alkoholProcent){
        Drikkevare d3 = new Drikkevare(navn, 0, Varetype.SPIRITUS,alkoholProcent);
        Storage.getStorage().addVare(d3);
        return d3;
    }

    public static Rundvisning createRundvisning(String navn, int antalPersoner, LocalDateTime tidspunkt){
        Rundvisning r = new Rundvisning(navn, Varetype.RUNDVISNING, antalPersoner, tidspunkt);
        Storage.getStorage().addVare(r);
        return r;
    }

    public static Diverse createBeklædning(String navn, String beskrivelse){
        Diverse div = new Diverse(navn, 0, Varetype.BEKLÆDNING, beskrivelse);
        Storage.getStorage().addVare(div);
        return div;
    }

    public static Diverse createMalt(String navn, String beskrivelse){
        Diverse div = new Diverse(navn, 0, Varetype.MALT, beskrivelse);
        Storage.getStorage().addVare(div);
        return div;
    }

    public static Diverse createGlas(String navn, String beskrivelse){
        Diverse div = new Diverse(navn, 0, Varetype.GLAS, beskrivelse);
        Storage.getStorage().addVare(div);
        return div;
    }

    public static Udlejningsvare createFustage(String navn){
        Udlejningsvare u1 = new Udlejningsvare(navn, 200, Varetype.FUSTAGE);
        Storage.getStorage().addVare(u1);
        return u1;
    }

    public static Udlejningsvare createAnlæg(String navn){
        Udlejningsvare u2 = new Udlejningsvare(navn, 0, Varetype.ANLÆG);
        Storage.getStorage().addVare(u2);
        return u2;
    }

    public static Udlejningsvare createKulsyre(String navn){
        Udlejningsvare u3 = new Udlejningsvare(navn, 1000, Varetype.KULSYRE);
        Storage.getStorage().addVare(u3);
        return u3;
    }



    public static void initStorage(){

        Klippekort k1 = new Klippekort(1, "Omar");
        Klippekort k2 = new Klippekort(2, "Mads");
        Klippekort k3 = new Klippekort(3, "Jens");
        Klippekort k4 = new Klippekort(4, "Mike");

        Sampakning s1 = new Sampakning("2 øl & 2 glas i gaveæske", 0, "Gaveæske", 2, 2, 20);
        Sampakning s5 = new Sampakning("4 øl i gaveæske", 0, "Gaveæske", 4, 0, 20);
        Sampakning s2 = new Sampakning("6 øl i trækasse ", 0, "Trækasse", 6, 0, 25);
        Sampakning s3 = new Sampakning("6 øl & 2 glas i gavekurv", 0, "Gavekurv", 2, 4, 30);
        Sampakning s6 = new Sampakning("6 øl & 6 glas i trækasse ", 0, "Trækasse", 6, 6, 25);
        Sampakning s6 = new Sampakning("12 øl i Trækassse", 0, "Trækasse", 12, 0, 25);
        Sampakning s4 = new Sampakning("12 øl i papkasse", 0, "Papkasse", 12, 0, 30);

        // ---- Opret fadøl objekter ------ ----------------------
        Controller.createFadøl("Klosterbryg", 8.00);
        Controller.createFadøl("Jazz Classic", 6.00);
        Controller.createFadøl("Extra Pilsner", 6.40);
        Controller.createFadøl("Celebration", 5.20);
        Controller.createFadøl("Blondie",5.00);
        Controller.createFadøl("Forårsbryg",5.50);
        Controller.createFadøl("India Pale Ale",7.00);
        Controller.createFadøl("Julebryg", 6.4);
        Controller.createFadøl("Imperial Stout", 9.00);
        Controller.createFadøl("Special", 7.50);

        //---- Opret flaske objekter -----------------------------
        Controller.createFlaske("Klosterbryg", 6.40);
        Controller.createFlaske("Sweet Georgia Brown", 7.50);
        Controller.createFlaske("Extra Pilsner", 8.00);
        Controller.createFlaske("Celebration", 6.40);
        Controller.createFlaske("Blondie", 4.80);
        Controller.createFlaske("Forårsbryg", 5.50);
        Controller.createFlaske("India Pale Ale", 6.40);
        Controller.createFlaske("Julebryg", 7.20);
        Controller.createFlaske("Juletønden", 7.20);
        Controller.createFlaske("Old Strong Ale", 9.5);
        Controller.createFlaske("Fregatten Jylland", 6.40);
        Controller.createFlaske("Imperial Stout", 7.40);
        Controller.createFlaske("Tribute", 6.40);
        Controller.createFlaske("Black Monster", 8.40);

        //---- Opret spiritus objekter ---------------------------
        Controller.createSpiritus("Whisky 45% 50cl rør", 45.00);
        Controller.createSpiritus("Whisky 45% 4 cl", 45.00);
        Controller.createSpiritus("Whisky 43% 50cl rør", 43.00);
        Controller.createSpiritus("Whisky med egesplint", 45.00);
        Controller.createSpiritus("Whisky uden egesplint", 45.00);
        Controller.createSpiritus("Liquor of Aarhus", 30.00);
        Controller.createSpiritus("Lyng gin 50 cl", 45.00);
        Controller.createSpiritus("Lyng gin 4 cl", 45.00);

        //---- Opret fustage objekter ---------------------------
        Controller.createFustage("Klosterbryg");
        Controller.createFustage("Jazz Classic");
        Controller.createFustage("Extra Pilsner");
        Controller.createFustage("Celebration");
        Controller.createFustage("Blondie");
        Controller.createFustage("Forårsbryg");
        Controller.createFustage("India Pale Ale");
        Controller.createFustage("Julebryg");
        Controller.createFustage("Imperial Stout");

        //---- Opret rundvisning objekter -----------------------
        Controller.createRundvisning("Carlsberg spionage", 5, LocalDateTime.of(2022, 4, 8, 12, 30));

        //---- Opret beklædning objekter ------------------------
        Controller.createBeklædning("T-shirt","Aprikos t-shirt med logo");
        Controller.createBeklædning("T-shirt", "Kongeblå t-shirt med logo");
        Controller.createBeklædning("T-shirt", "Basillikum t-shirt med logo");
        Controller.createBeklædning("Polo", "Gran grøn polo med logo");
        Controller.createBeklædning("Polo", "Blå polo med logo");
        Controller.createBeklædning("Polo", "Lilla polo med logo");
        Controller.createBeklædning("Cap", "Knækket rød cap med logo");
        Controller.createBeklædning("Cap", "Turkise cap med logo");
        Controller.createBeklædning("Cap", "Brændt orange cap med logo");

        //---- Opret malt objekter ------------------------------
        Controller.createMalt("Malt", "25 kg sæk");

        //---- Opret glas objekter ------------------------------
        Controller.createGlas("1 glas", "1 glas");
        Controller.createGlas("2 whiskyglas + 2 brikker", "2 whiskyglas + 2 brikker");

        //--- Opret anlæg objekter ------------------------------
        Controller.createAnlæg("1-hane");
        Controller.createAnlæg("2-haner");
        Controller.createAnlæg("Bar med flere haner");
        Controller.createAnlæg("Levering");
        Controller.createAnlæg("Krus");

        //---- Opret kulsyre objekter ----------------------------
        Controller.createKulsyre("6 kg");
        Controller.createKulsyre("4 kg");
        Controller.createKulsyre("10 kg");

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

    // TODO: Mike brug den her i stedet pls
    public ArrayList<String> getPrisgrupperByName(){        ArrayList<String> rl = new ArrayList<>();
        for(Vare v : Storage.getStorage().getVarer()){
            for(Prisgruppe pg : v.getPrisgrupper()){
                if(!rl.contains(pg.getNavn())){
                    rl.add(pg.getNavn());
                }
            }
        }
        return rl;
    }

    public void setActivePrisgruppe(String pgNavn){
        Vare v = null;
        for(int i = 0; i < Storage.getStorage().getVarer().size(); i++){
            v = Storage.getStorage().getVarer().get(i);
            v.setAktivPrisgruppe(pgNavn);
        }
    }
}
