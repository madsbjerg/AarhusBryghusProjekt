package Application.Controller;

import Application.Models.*;
import Storage.Storage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Storage.Storage;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private static Controller controller;

    public static Controller getController(){
        if(controller == null) controller = new Controller();
        return controller;
    }

    public  Drikkevare createFadøl(String navn,double alkoholProcent){
        Drikkevare d1 = new Drikkevare(navn, 0, Varetype.FADØL, alkoholProcent);
        Storage.getStorage().addVare(d1);
        return d1;
    }

    public  Drikkevare createFlaske(String navn, double alkoholProcent){
        Drikkevare d2 = new Drikkevare(navn, 0, Varetype.FLASKE,alkoholProcent);
        Storage.getStorage().addVare(d2);
        return d2;
    }

    public  Drikkevare createSpiritus(String navn, double alkoholProcent){
        Drikkevare d3 = new Drikkevare(navn, 0, Varetype.SPIRITUS,alkoholProcent);
        Storage.getStorage().addVare(d3);
        return d3;
    }

    public  Rundvisning createRundvisning(String navn, int antalPersoner, LocalDateTime tidspunkt){
        if(LocalDateTime.now().plusDays(13).isBefore(tidspunkt)) throw new IllegalArgumentException("Tidspunkt er efter 14 dage af oprettelse af rundvisning.");
        Rundvisning r = new Rundvisning(navn, Varetype.RUNDVISNING, antalPersoner, tidspunkt);
        Storage.getStorage().addVare(r);
        return r;
    }

    public  Diverse createBeklædning(String navn, String beskrivelse){
        Diverse div = new Diverse(navn, 0, Varetype.BEKLÆDNING, beskrivelse);
        Storage.getStorage().addVare(div);
        return div;
    }

    public  Diverse createMalt(String navn, String beskrivelse){
        Diverse div = new Diverse(navn, 0, Varetype.MALT, beskrivelse);
        Storage.getStorage().addVare(div);
        return div;
    }

    public  Diverse createGlas(String navn, String beskrivelse){
        Diverse div = new Diverse(navn, 0, Varetype.GLAS, beskrivelse);
        Storage.getStorage().addVare(div);
        return div;
    }

    public  Udlejningsvare createFustage(String navn){
        Udlejningsvare u1 = new Udlejningsvare(navn, 200, Varetype.FUSTAGE);
        Storage.getStorage().addVare(u1);
        return u1;
    }

    public  Udlejningsvare createAnlæg(String navn){
        Udlejningsvare u2 = new Udlejningsvare(navn, 0, Varetype.ANLÆG);
        Storage.getStorage().addVare(u2);
        return u2;
    }

    public  Udlejningsvare createKulsyre(String navn){
        Udlejningsvare u3 = new Udlejningsvare(navn, 1000, Varetype.KULSYRE);
        Storage.getStorage().addVare(u3);
        return u3;
    }

    public  Klippekort createKlippekort(String navnKunde){
        Klippekort k = new Klippekort("hans");
        Storage.getStorage().addVare(k);
        return k;
    }

    public Klippekort createKlippekort(){
        Klippekort k = new Klippekort();
        Storage.getStorage().addVare(k);
        return k;
    }
    public  ProduktSalg createProduktSalg(HashMap<Vare, Integer> varer, Betalingsform bform, double beloeb, Rabat rabat){

        ProduktSalg p1 = new ProduktSalg(varer, beloeb, bform, rabat);
        Storage.getStorage().addSalg(p1);
        return p1;
    }

    public  Rabat createFastRabat(double rabatBeloeb){
        Rabat r = new FastRabat(rabatBeloeb);
        return r;
    }

    public  Rabat createProcentRabat(double procentSats){
        Rabat r = new ProcentRabat(procentSats);
        return r;
    }

    public  Regning createRegning (HashMap<Vare, Integer> varer, Betalingsform betalingsform,Rabat rabat, double beloebTotal, String navnKunde){
        if(beloebTotal <= 0) throw new IllegalArgumentException("Beløb for regning skal være højere end 0.");
        Regning regning = new Regning(varer, betalingsform, rabat, beloebTotal, navnKunde);
        Storage.getStorage().addSalg(regning);
        return regning;
    }

    public Udlejning createUdlejning(HashMap<Vare, Integer> varer, double pant, LocalDate startDato, LocalDate slutDato,
                                            Betalingsform betalingsform, Rabat rabat) {
        if(slutDato.isEqual(startDato) || slutDato.isBefore(startDato)){
            throw new IllegalArgumentException("Startdato skal være før slutdato.");
        }else{
            Udlejning udlejning = new Udlejning(varer, pant, startDato, slutDato, betalingsform, rabat);
            Storage.getStorage().addSalg(udlejning);
            return udlejning;
        }
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

    public int brugKlippekort(Klippekort klippekort, int klipPris){
        klippekort.brugKlip(klipPris);
        return klippekort.getAntalKlip();
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

    // TODO: Mike brug den her i stedet pls: Author Mads
    public ArrayList<String> getPrisgrupperByName(){
        ArrayList<String> rl = new ArrayList<>();
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

    public void resetPrisgrupper(){
        Vare v = null;
        for (int i = 0; i < Storage.getStorage().getVarer().size(); i++){
            v = Storage.getStorage().getVarer().get(i);
            v.setAktivPrisgruppe(null);
        }
    }

    public  double totalPris(String pgnavn, HashMap<Vare, Integer> varer){
        double sum = 0;
        for(Vare vare : varer.keySet()){
            sum += vare.getPris(pgnavn) * varer.get(vare);
        }
        return sum;
    }
                                        //Varen, Antallet af den vare.
    public double totalUdlejning(HashMap<Vare, Integer> varer, HashMap<Vare, Integer> returnerede){
        double sumVarer = 0;
        double sumPant = 0;
        double totalUdlejning = 0;

        for(Vare vare : varer.keySet()){
            sumVarer += vare.getPrisgrupper().get(0).getPris() * varer.get(vare);
        }
        for(Vare vare : returnerede.keySet()){
            sumPant += vare.getPant() * returnerede.get(vare);
        }
        totalUdlejning = sumVarer - sumPant;
        return totalUdlejning;
    }

    public double beregnPant(HashMap<Vare, Integer> varer){

        double totalPant = 0;

        for(Vare vare : varer.keySet()){
            totalPant += vare.getPant() * varer.get(vare);
        }
        return totalPant;
    }

    public void setBetaltUdlejning(Udlejning udlejning){
        udlejning.setBetalt(true);
    }

    public void saveStorageToFile(){
        try{
            FileOutputStream fs_out = new FileOutputStream("bryghus.ser");
            ObjectOutputStream os_out = new ObjectOutputStream(fs_out);
            for(Vare v : Storage.getStorage().getVarer()){
                os_out.writeObject(v);
            }
            for(Salg s : Storage.getStorage().getSalg()){
                os_out.writeObject(s);
            }
            os_out.close();
            fs_out.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage() + " " + ex.getStackTrace());
        }
    }

    public  void loadStorageFromFile(){
        try{
            File f = new File("bryghus.ser");
            if(f.exists()){
                FileInputStream fs_in = new FileInputStream(f);
                ObjectInputStream os_in = new ObjectInputStream(fs_in);
                boolean isNotDone = true;
                while(fs_in.available() > 0){
                    Object obj = os_in.readObject();
                    if(obj instanceof Vare){
                        Storage.getStorage().addVare((Vare)obj);
                    }
                    else if(obj instanceof Salg){
                        Storage.getStorage().addSalg((Salg)obj);
                    }
                }
                os_in.close();
                fs_in.close();
            }
            else{
                this.initStorage();
            }

        }catch(IOException | ClassNotFoundException ex){
            System.out.println(ex.getMessage() + " " + ex.getStackTrace() + ex);
        }
    }



    public void initStorage(){
        Sampakning s1 = new Sampakning("2 øl & 2 glas i gaveæske", 0, "Gaveæske", 2, 2);
        Sampakning s5 = new Sampakning("4 øl i gaveæske", 0, "Gaveæske", 4, 0);
        Sampakning s2 = new Sampakning("6 øl i trækasse ", 0, "Trækasse", 6, 0);
        Sampakning s3 = new Sampakning("6 øl & 2 glas i gavekurv", 0, "Gavekurv", 2, 4);
        Sampakning s6 = new Sampakning("6 øl & 6 glas i trækasse ", 0, "Trækasse", 6, 6);
        Sampakning s7 = new Sampakning("12 øl i Trækassse", 0, "Trækasse", 12, 0);
        Sampakning s4 = new Sampakning("12 øl i papkasse", 0, "Papkasse", 12, 0);

        // ---- Opret fadøl objekter ------ ----------------------

        Prisgruppe pgFredagsbar38 = new Prisgruppe(38, "Fredagsbar");
        Prisgruppe pgFredagsbar1Klip = new Prisgruppe(1, "FredagsbarKlip");

        Drikkevare d =  controller.createFadøl("Jazz Classic",  6.00);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("Klosterbryg",  8.00);
        d.addPrisgruppe(pgFredagsbar1Klip);
        d.addPrisgruppe(pgFredagsbar38);

        d = controller.createFadøl("Extra Pilsner",  6.40);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("Celebration",  5.20);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("Blondie",  5.00);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("Forårsbryg",  5.50);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("India Pale Ale",  7.00);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("Julebryg",  6.4);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("Imperial Stout",  9.00);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        d = controller.createFadøl("Special",  7.50);
        d.addPrisgruppe(pgFredagsbar38);
        d.addPrisgruppe(pgFredagsbar1Klip);

        //---- Opret flaske objekter -----------------------------
        Prisgruppe pgFredagsBar70 = new Prisgruppe(70, "Fredagsbar");
        Prisgruppe pgFredagsbar2Klip = new Prisgruppe(2, "FredagsbarKlip");
        Prisgruppe pgButikPris36 = new Prisgruppe(36, "Butik");
        Prisgruppe pgButikPris60 = new Prisgruppe(60, "Butik");
        Prisgruppe pgFredagsbar100 = new Prisgruppe(100, "Fredagsbar");
        Prisgruppe pgFredagsbar3Klip = new Prisgruppe(3, "FredagsbarKlip");
        d = controller.createFlaske("Klosterbryg", 6.40);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Sweet Georgia Brown", 7.50);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Extra Pilsner", 8.00);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Celebration", 6.40);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Blondie", 4.80);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Forårsbryg", 5.50);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("India Pale Ale", 6.40);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Julebryg", 7.20);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Juletønden", 7.20);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Old Strong Ale", 9.5);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Fregatten Jylland", 6.40);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Imperial Stout", 7.40);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Tribute", 6.40);
        d.addPrisgruppe(pgButikPris36);
        d.addPrisgruppe(pgFredagsbar2Klip);
        d.addPrisgruppe(pgFredagsBar70);
        d = controller.createFlaske("Black Monster", 8.40);
        d.addPrisgruppe(pgButikPris60);
        d.addPrisgruppe(pgFredagsbar3Klip);
        d.addPrisgruppe(pgFredagsbar100);

        //---- Opret spiritus objekter ---------------------------
        Prisgruppe pgButik599 = new Prisgruppe(599,"Butik");
        Prisgruppe pgFredagsbar599 = new Prisgruppe(599, "Fredagsbar");
        Prisgruppe pgFredagsbar50 = new Prisgruppe(50, "Fredagsbar");
        Prisgruppe pgButik499 = new Prisgruppe(499, "Butik");
        Prisgruppe pgFredagsbar499 = new Prisgruppe(499, "Fredagsbar");
        Prisgruppe pgButik300 = new Prisgruppe(300, "Butik");
        Prisgruppe pgFredagsbar300 = new Prisgruppe(300, "Fredagsbar");
        Prisgruppe pgButik350 = new Prisgruppe(350, "Butik");
        Prisgruppe pgFredagsbar350 = new Prisgruppe(350, "Fredagsbar");
        Prisgruppe pgButik80 = new Prisgruppe(80, "Butik");
        Prisgruppe pgFredagsbar80 = new Prisgruppe(80, "Fredagsbar");
        Prisgruppe pgButik175 = new Prisgruppe(175, "Butik");
        Prisgruppe pgFredagsbar175 = new Prisgruppe(175, "Fredagsbar");
        Prisgruppe pgFredagsbar40 = new Prisgruppe(40, "Fredagsbar");
        d = controller.createSpiritus("Whisky 45% 50cl rør", 45.00);
        d.addPrisgruppe(pgButik599);
        d.addPrisgruppe(pgFredagsbar599);

        d = controller.createSpiritus("Whisky 45% 4 cl", 45.00);
        d.addPrisgruppe(pgFredagsbar50);

        d=controller.createSpiritus("Whisky 43% 50cl rør", 43.00);
        d.addPrisgruppe(pgButik499);
        d.addPrisgruppe(pgFredagsbar499);

        d=controller.createSpiritus("Whisky med egesplint", 45.00);
        d.addPrisgruppe(pgButik350);
        d.addPrisgruppe(pgFredagsbar350);

        d=controller.createSpiritus("Whisky uden egesplint", 45.00);
        d.addPrisgruppe(pgButik300);
        d.addPrisgruppe(pgFredagsbar300);
        d=controller.createSpiritus("Liquor of Aarhus", 30.00);
        d.addPrisgruppe(pgButik175);
        d.addPrisgruppe(pgFredagsbar175);
        d=controller.createSpiritus("Lyng gin 50 cl", 45.00);
        d.addPrisgruppe(pgButik175);
        d.addPrisgruppe(pgFredagsbar175);
        d=controller.createSpiritus("Lyng gin 4 cl", 45.00);
        d.addPrisgruppe(pgFredagsbar40);

        //---- Opret fustage objekter ---------------------------

        Prisgruppe pgFustageButik775 = new Prisgruppe(775, "Butik");
        Prisgruppe pgFustageButik625 = new Prisgruppe(625, "Butik");
        Prisgruppe pgFustageButik575 = new Prisgruppe(575, "Butik");
        Prisgruppe pgFustageButik700 = new Prisgruppe(700, "Butik");

        Udlejningsvare f = controller.createFustage("Klosterbryg");
        f.addPrisgruppe(pgFustageButik775);
        f = controller.createFustage("Jazz Classic");
        f.addPrisgruppe(pgFustageButik625);
        f = controller.createFustage("Extra Pilsner");
        f.addPrisgruppe(pgFustageButik575);
        f = controller.createFustage("Celebration");
        f.addPrisgruppe(pgFustageButik775);
        f = controller.createFustage("Blondie");
        f.addPrisgruppe(pgFustageButik700);
        f = controller.createFustage("Forårsbryg");
        f.addPrisgruppe(pgFustageButik775);
        f = controller.createFustage("India Pale Ale");
        f.addPrisgruppe(pgFustageButik775);
        f = controller.createFustage("Julebryg");
        f.addPrisgruppe(pgFustageButik775);
        f = controller.createFustage("Imperial Stout");
        f.addPrisgruppe(pgFustageButik775);

        //---- Opret rundvisning objekter -----------------------
        controller.createRundvisning("Carlsberg spionage", 5, LocalDateTime.of(2022, 4, 8, 12, 30));

        //---- Opret beklædning objekter ------------------------

        Prisgruppe pgFredagsbar70 = new Prisgruppe(70, "Fredagsbar");
        Prisgruppe pgButik70 = new Prisgruppe(70, "Butik");
        Prisgruppe pgButik100 = new Prisgruppe(100, "Butik");
        Prisgruppe pgFredagsbar30 = new Prisgruppe(30, "Fredagsbar");
        Prisgruppe pgButik30 = new Prisgruppe(30, "Butik");

        Diverse di = controller.createBeklædning("T-shirt","Aprikos t-shirt med logo");
        di.addPrisgruppe(pgFredagsBar70);
        di.addPrisgruppe(pgButik70);

        di = controller.createBeklædning("T-shirt", "Kongeblå t-shirt med logo");
        di.addPrisgruppe(pgFredagsBar70);
        di.addPrisgruppe(pgButik70);

        di = controller.createBeklædning("T-shirt", "Basillikum t-shirt med logo");
        di.addPrisgruppe(pgFredagsBar70);
        di.addPrisgruppe(pgButik70);

        di = controller.createBeklædning("Polo", "Gran grøn polo med logo");
        di.addPrisgruppe(pgFredagsbar100);
        di.addPrisgruppe(pgButik100);

        di = controller.createBeklædning("Polo", "Blå polo med logo");
        di.addPrisgruppe(pgFredagsbar100);
        di.addPrisgruppe(pgButik100);

        di = controller.createBeklædning("Polo", "Lilla polo med logo");
        di.addPrisgruppe(pgFredagsbar100);
        di.addPrisgruppe(pgButik100);

        di = controller.createBeklædning("Cap", "Knækket rød cap med logo");
        di.addPrisgruppe(pgFredagsbar30);
        di.addPrisgruppe(pgButik30);

        di = controller.createBeklædning("Cap", "Turkise cap med logo");
        di.addPrisgruppe(pgFredagsbar30);
        di.addPrisgruppe(pgButik30);

        di = controller.createBeklædning("Cap", "Brændt orange cap med logo");
        di.addPrisgruppe(pgFredagsbar30);
        di.addPrisgruppe(pgButik30);


        //---- Opret malt objekter ------------------------------

        Prisgruppe pgMaltButik = new Prisgruppe(300, "Butik");
        Diverse div = controller.createMalt("Malt", "25 kg sæk");
        div.addPrisgruppe(pgMaltButik);


        //---- Opret glas objekter ------------------------------

        Prisgruppe pgGlas1Butik = new Prisgruppe(15, "Butik");
        Prisgruppe pgWhiskyOgBrikkerButik = new Prisgruppe(80, "Butik");
        Prisgruppe pgWhiskyOgBrikkerFredagsbar = new Prisgruppe(80, "Fredagsbar");

        div = controller.createGlas("1 glas", "1 glas");
        div.addPrisgruppe(pgGlas1Butik);
        div = controller.createGlas("2 whiskyglas + 2 brikker", "2 whiskyglas + 2 brikker");
        div.addPrisgruppe(pgWhiskyOgBrikkerButik);
        div.addPrisgruppe(pgWhiskyOgBrikkerFredagsbar);



        //--- Opret anlæg objekter
        Prisgruppe pgButik250 = new Prisgruppe(250, "Butik");
        Prisgruppe pgButik400 = new Prisgruppe(400, "Butik");
        Prisgruppe pgButik500 = new Prisgruppe(500, "Butik");
        Prisgruppe pgButik60 = new Prisgruppe(60, "Butik");

        Udlejningsvare ud = controller.createAnlæg("1-hane");
        ud.addPrisgruppe(pgButik250);

        ud = controller.createAnlæg("2-haner");
        ud.addPrisgruppe(pgButik400);

        ud = controller.createAnlæg("Bar med flere haner");
        ud.addPrisgruppe(pgButik500);

        ud = controller.createAnlæg("Levering");
        ud.addPrisgruppe(pgButik500);

        ud = controller.createAnlæg("Krus");
        ud.addPrisgruppe(pgButik60);

        //---- Opret kulsyre objekter ----------------------------
        Prisgruppe pgKulsyreFredagsbar400 = new Prisgruppe(400, "Fredagsbar");
        Prisgruppe pgKulsyreButik400 = new Prisgruppe(400, "Butik");

        Udlejningsvare u = controller.createKulsyre("6 kg ");
        u.addPrisgruppe(pgKulsyreFredagsbar400);
        u.addPrisgruppe(pgKulsyreButik400);

        u = controller.createKulsyre("4 kg");
        u.addPrisgruppe(pgKulsyreFredagsbar400);
        u.addPrisgruppe(pgKulsyreButik400);

        u = controller.createKulsyre("10 kg");
        u.addPrisgruppe(pgKulsyreFredagsbar400);
        u.addPrisgruppe(pgKulsyreButik400);


        //---- Opret klippekort --------------------------------
        controller.createKlippekort("hans");
        controller.createKlippekort("gert");
        controller.createKlippekort("Jens");
        controller.createKlippekort("Mads");

        //Klippekort objektet til salg af klippekort
        Klippekort klippekort = controller.createKlippekort();
        Prisgruppe prisgruppeButik  = new Prisgruppe(130, "Butik");
        Prisgruppe prisgruppeFredagsbar = new Prisgruppe(130, "Fredagsbar");
        klippekort.addPrisgruppe(prisgruppeButik);
        klippekort.addPrisgruppe(prisgruppeFredagsbar);

        controller.saveStorageToFile();
    }

}
