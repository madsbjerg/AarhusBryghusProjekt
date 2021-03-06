package Application.Controller;

import Application.Models.*;
import Storage.Storage;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {




    @org.junit.jupiter.api.Test
    void getPrisgrupperByName() {

        Prisgruppe pgTest1 = new Prisgruppe(100, "ButikTest");
        Prisgruppe pgTest2 = new Prisgruppe(100.5, "Test2");

        Controller testController = new Controller();

        Drikkevare d1 = testController.createFlaske("Test", 0);
        d1.addPrisgruppe(pgTest1);

        Drikkevare d2 = testController.createSpiritus("Ol fashioned", 50);
        d2.addPrisgruppe(pgTest2);

        ArrayList<String> testList = testController.getPrisgrupperByName();

        assertEquals("ButikTest", testList.get(0));
        assertEquals("Test2", testList.get(1));



    }


    @org.junit.jupiter.api.Test
    void resetPrisgrupper() {
        // Omar
    }

    @org.junit.jupiter.api.Test
    void totalPris() {

        Controller testController = new Controller();


        Prisgruppe pgTest = new Prisgruppe(100, "Butik");
        Prisgruppe pgTest2 = new Prisgruppe(100.5, "Butik");
        Drikkevare testvare = new Drikkevare("testvare", 0, Varetype.FLASKE, 0);
        Drikkevare testvare2 = new Drikkevare("Testvare2", 0, Varetype.FLASKE, 0);
        Drikkevare testvare3 = new Drikkevare("Testvare3", 0, Varetype.FLASKE, 0);
        testvare.addPrisgruppe(pgTest);
        testvare2.addPrisgruppe(pgTest2);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testvare, 2);
        HashMap<Vare, Integer> varer2 = new HashMap<>();
        varer2.put(testvare2, 0);

        double TC1 = testController.totalPris(pgTest.getNavn(), varer);
        double TC2 = testController.totalPris(pgTest2.getNavn(),varer2);
        
        assertEquals(200, TC1);
        assertEquals(0, TC2);

    }

    @Test
    void createSpiritus() {
        // Mike

        Controller testcontroller = new Controller();
        //nedre gr??nse
        Exception exception =assertThrows(IllegalArgumentException.class, ()-> testcontroller.createSpiritus("??l", -15));
        //??vre gr??nse
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> testcontroller.createSpiritus("??l1", 5000));
        //normalt flow
        //Nedre gyldig gr??nsev??rdi
        Drikkevare drikkevare = testcontroller.createSpiritus("l", 1);
        assertEquals(1, drikkevare.getAlkoholProcent());
        //mellem
        Drikkevare drikkevare1 = testcontroller.createSpiritus("p",50);
        assertEquals(50, drikkevare1.getAlkoholProcent());
        //??vre gyldig gr??nsev??rdi
        Drikkevare drikkevare2 = testcontroller.createSpiritus("lksda", 99);
        assertEquals(99, drikkevare2.getAlkoholProcent());

    }
    // Sp??rgsm??l: Hvorfor har vi beloeb i constructoren, n??r vi alligevel beregner bel??bet i totalPris metoden?
    // Den her metode er et helvede at teste.. G??r det imorgen i skolen.


    @Test
    void createKlippekort() {
        // Arrange
        Controller c = new Controller();
        // Act
        Klippekort k = c.createKlippekort("Mads");
        // Assert
        assertEquals(4, k.getAntalKlip());
    }


    @Test
    void createProduktSalgBetalingsForm() {
        Controller testController = new Controller();

        Prisgruppe pgTest = new Prisgruppe(100, "Butik");

        Drikkevare testvare = new Drikkevare("testvare", 0, Varetype.FLASKE, 0);
        testvare.addPrisgruppe(pgTest);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testvare, 2);

        double total = testController.totalPris("Butik", varer);

        ProduktSalg produktSalgRegning =testController.createProduktSalg(varer,Betalingsform.REGNING,total,null);

        //test uden betalingsform
        Exception exception = assertThrows(IllegalArgumentException.class, () -> testController.createProduktSalg(varer, null, total, null));
        //normalt flow
        assertSame(Betalingsform.REGNING,produktSalgRegning.getBetalingsform() );

    }

    @Test
    void createProduktSalgTestP??Varer(){
        Controller testController = new Controller();

        Prisgruppe pgTest = new Prisgruppe(100, "Butik");

        Drikkevare testvare = new Drikkevare("testvare", 0, Varetype.FLASKE, 0);
        testvare.addPrisgruppe(pgTest);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testvare, 2);

        //Test uden vare.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> testController.createProduktSalg(null, Betalingsform.KREDITKORT, 0, null));
        // salg med varer
        ProduktSalg produktSalg = testController.createProduktSalg(varer, Betalingsform.KREDITKORT,  0,   null);
        assertSame(varer, produktSalg.getVare());

    }

    @Test
    void createProduktSalgBeloeb(){
        Controller testController = new Controller();

        Prisgruppe pgTest = new Prisgruppe(100, "Butik");

        Drikkevare testvare = new Drikkevare("testvare", 0, Varetype.FLASKE, 0);
        testvare.addPrisgruppe(pgTest);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testvare, 2);

        double total = testController.totalPris("Butik", varer);

        //Test for beloeb <0
         Exception exception =assertThrows(IllegalArgumentException.class, () -> testController.createProduktSalg(varer, Betalingsform.KREDITKORT,-100,null));
       //normalt flow
        ProduktSalg produktSalg = testController.createProduktSalg(varer, Betalingsform.KREDITKORT,total,null);
        assertEquals(total, produktSalg.getBeloeb());
    }

    @Test
    void createProduktSalgRabat(){
        Controller testController = new Controller();

        Prisgruppe pgTest = new Prisgruppe(100, "Butik");

        Drikkevare testvare = new Drikkevare("testvare", 0, Varetype.FLASKE, 0);
        testvare.addPrisgruppe(pgTest);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testvare, 2);

        double total = testController.totalPris("Butik", varer);

        Rabat rabatFast = testController.createFastRabat(50);
        Rabat rabatProcent = testController.createProcentRabat(5);

        ProduktSalg produktSalgUden = testController.createProduktSalg(varer, Betalingsform.KREDITKORT  ,total,null);
        ProduktSalg produktSalgFast = testController.createProduktSalg(varer, Betalingsform.KREDITKORT,total,rabatFast);
        ProduktSalg produktSalgProcent = testController.createProduktSalg(varer,Betalingsform.KREDITKORT,total,rabatProcent);

        //Uden rabat
        assertSame(null, produktSalgUden.getRabat());
        //Fast rabat
        assertSame(rabatFast, produktSalgFast.getRabat());
        //Procent rabat
        assertSame(rabatProcent, produktSalgProcent.getRabat());
    }


    @Test
    void createFastRabat(){
        Controller c = new Controller();

        Rabat r = c.createFastRabat(50);
        Rabat r1 = c.createFastRabat(20);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->c.createFastRabat(-30));
        assertEquals("Rabat skal v??re over 0", ex.getMessage());

        assertEquals(150, r.beregnRabat(200));
        assertEquals(180, r1.beregnRabat(200));




    }






    @Test
    void createProcentRabat() {
        Controller testController = new Controller();
        //Tjekker exceptions
        //Nedre gr??nse
        Exception exception = assertThrows(IllegalArgumentException.class, () -> testController.createProcentRabat(-11));
        //??vre gr??nse.
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> testController.createProcentRabat(5000));

        //gyldige v??rdier:
        Rabat rabat = testController.createProcentRabat(1);
        Rabat rabat1 = testController.createProcentRabat(50);
        Rabat rabat2 = testController.createProcentRabat(99);
        //nedre gyldig
        // 100 * 1% = 1
        assertEquals(99, rabat.beregnRabat(100),0.01);
        //midt gyldig
        //100 * 50% = 50
        assertEquals(50, rabat1.beregnRabat(100),0.01);
        //topgyldig.
        //100 * 99% = 99
        assertEquals(1, rabat2.beregnRabat(100),0.01);
    }

    @Test
    void createRegning() {
        // Arrange
        Controller c = new Controller();
        Vare testVare = new Drikkevare("testVare", 0, Varetype.FAD??L, 0);
        Prisgruppe testPg = new Prisgruppe(100,"testPrisGruppe");
        testVare.addPrisgruppe(testPg);
        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testVare, 1);

        // Act
        Regning r0 = c.createRegning(varer, Betalingsform.REGNING, null, 1, "testNavn0");

        // Assert r0
        assertFalse(r0.isBetalt());
        assertEquals(1, r0.getBeloebTotal());
        assertEquals("testNavn0", r0.getNavnKunde());

        // Assert exceptional arguments
        Exception ex = assertThrows(IllegalArgumentException.class, () -> c.createRegning(varer, Betalingsform.REGNING, null, 0, "testNavn1"));
        assertEquals("Bel??b for regning skal v??re h??jere end 0.", ex.getMessage());
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> c.createRegning(varer, Betalingsform.REGNING, null, -1, "testNavn1"));
        assertEquals("Bel??b for regning skal v??re h??jere end 0.", ex1.getMessage());
    }

    @Test
    void createUdlejning() {
        // Mads
        // Arrange
        Controller c = new Controller();
        Vare testVare = new Udlejningsvare("testVare", 100, Varetype.FUSTAGE);
        Prisgruppe testPg = new Prisgruppe(100,"testPrisGruppe");
        testVare.addPrisgruppe(testPg);
        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testVare, 1);

        // Act
        Udlejning u = c.createUdlejning(varer, 100, LocalDate.of(1999, 1,1), LocalDate.of(1999, 1, 2),Betalingsform.KONTANT, null);

        // Assert
        assertEquals(varer, u.getVarer());
        assertEquals(100, u.getPantBeloeb());
        assertEquals(new Udlejning(varer, 100, LocalDate.of(1999, 1,1), LocalDate.of(1999, 1, 2),Betalingsform.KONTANT, null), u);

        // Assert exceptional arguments
        Exception ex = assertThrows(IllegalArgumentException.class, () -> c.createUdlejning(varer, 100, LocalDate.of(1999, 1,1), LocalDate.of(1999, 1, 1),Betalingsform.KONTANT, null));
        assertEquals("Startdato skal v??re f??r slutdato.", ex.getMessage());
        ex = assertThrows(IllegalArgumentException.class, () -> c.createUdlejning(varer, 100, LocalDate.of(1999, 1,2), LocalDate.of(1999, 1, 1),Betalingsform.KONTANT, null));
        assertEquals("Startdato skal v??re f??r slutdato.", ex.getMessage());
        //
    }


    @Test
    void testResetPrisgrupper() {
        // Jens
        // Arrange
        Controller testController = new Controller();

        Prisgruppe pgTest = new Prisgruppe(100, "pgTest1");
        Drikkevare dtest = testController.createFlaske("TestFlaske1", 0);
        dtest.addPrisgruppe(pgTest);
        testController.setActivePrisgruppe("pgTest1");

        Prisgruppe pgTest2 = new Prisgruppe(50, "pgTest2");
        Drikkevare dtest2 = testController.createFlaske("Testflaske2", 0);
        dtest2.addPrisgruppe(pgTest2);
        testController.setActivePrisgruppe("pgTest2");

        // Act
        testController.resetPrisgrupper();

        // Assert
        assertEquals("Ingen prisgruppe valgt", dtest.getAktivPrisgruppe());
        assertEquals("Ingen prisgruppe valgt", dtest2.getAktivPrisgruppe());

    }

    @Test
    void beregnPant(){
        Controller c = new Controller();

        Vare testVare = new Udlejningsvare("testVare", 200, Varetype.FUSTAGE);
        Vare vare = new Udlejningsvare("6 kg", 1000, Varetype.KULSYRE);
        HashMap<Vare, Integer> varer = new HashMap<>();
        HashMap<Vare, Integer> vareIntegerHashMap = new HashMap<>();
        varer.put(testVare, 2);
        vareIntegerHashMap.put(vare, 2);


        //act
        Udlejningsvare u = c.createFustage("celebration");
        Udlejningsvare u1 = c.createKulsyre("6 kg");


        //asserts
        assertEquals(400, c.beregnPant(varer));
        assertEquals(2000, c.beregnPant(vareIntegerHashMap));




    }

    @Test
    void totalUdlejning(){
        Controller c = new Controller();

        Vare udlejning = new Udlejningsvare("Klosterbryg", 3000, Varetype.FUSTAGE);
        Vare u = new Udlejningsvare("6 kg",200, Varetype.KULSYRE);

        Prisgruppe pg = new Prisgruppe(775, "pgtest1");
        udlejning.addPrisgruppe(pg);
        Prisgruppe pg1 = new Prisgruppe(400, "pgtest2");
        u.addPrisgruppe(pg1);

        HashMap<Vare, Integer> vareHashMap = new HashMap<>();
        HashMap<Vare, Integer> vareIntegerHashMap = new HashMap<>();

        vareHashMap.put(udlejning, 1);
        vareIntegerHashMap.put(u,1);

        //asserts
        assertEquals(575, c.totalUdlejning(vareHashMap, vareIntegerHashMap));
        
    }

    @Test
    void brugKlippekort() {
        Controller testController = new Controller();

       Klippekort klippekort1 = testController.createKlippekort("hans");
       //tester om klippekortet bliver initialiseret med 4 klip.
       assertEquals(4,klippekort1.getAntalKlip());
       //tester om man kan forbruge klip.
        //3 klip brugt. 4-3 = 1
        testController.brugKlippekort(klippekort1, 3);
        assertEquals(1, klippekort1.getAntalKlip());
       //tester for exceptions.
       //Indtastning af negativ v??rdi i klippris param
        Exception exception = assertThrows(IllegalArgumentException.class, () ->testController.brugKlippekort(klippekort1, -15));
        //fors??g p??, at bruge flere klip end der eksistere.
        //1 klip tilbage. 2 fors??gt forbrugt (1-2) <0
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> testController.brugKlippekort(klippekort1, 2));

    }
}