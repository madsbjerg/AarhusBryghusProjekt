package Application.Controller;

import Application.Models.*;
import Storage.Storage;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
    void setActivePrisgruppe() {
        // Jens
        // Arrange
        Controller testController = new Controller();

        Prisgruppe pgtest1 = new Prisgruppe(100, "Test1");
        Prisgruppe pgtest2 = new Prisgruppe(50, "Test2");
        Prisgruppe pgtest3 = new Prisgruppe(0, "Test3");

        // Act
        testController.setActivePrisgruppe("Test1");
        testController.setActivePrisgruppe("Test2");
        testController.setActivePrisgruppe("Test3");

        // String testString = testController.getAktivePrisgrupper;
        // Føler jeg mangler en get i controlleren.

        // Assert
        assertEquals("Test1", "");



    }

    @org.junit.jupiter.api.Test
    void resetPrisgrupper() {
        // Omar
    }

    @org.junit.jupiter.api.Test
    void totalPris() {

        // TODO: Exception (valid operation/ IllegalState) i controller. Modificer TC3 til at give Exception.

        Controller testController = new Controller();


        Prisgruppe pgTest = new Prisgruppe(100, "Butik");
        Prisgruppe pgTest2 = new Prisgruppe(100.5, "Butik");
        Prisgruppe pgTest3 = new Prisgruppe(-100, "Butik");
        Drikkevare testvare = new Drikkevare("testvare", 0, Varetype.FLASKE, 0);
        Drikkevare testvare2 = new Drikkevare("Testvare2", 0, Varetype.FLASKE, 0);
        Drikkevare testvare3 = new Drikkevare("Testvare3", 0, Varetype.FLASKE, 0);
        testvare.addPrisgruppe(pgTest);
        testvare2.addPrisgruppe(pgTest2);
        testvare3.addPrisgruppe(pgTest3);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testvare, 2);
        HashMap<Vare, Integer> varer2 = new HashMap<>();
        varer2.put(testvare2, 0);
        HashMap<Vare, Integer> varer3 = new HashMap<>();
        varer3.put(testvare3, 2);

        double TC1 = testController.totalPris(pgTest.getNavn(), varer);
        double TC2 = testController.totalPris(pgTest2.getNavn(),varer2);
        double TC3 = testController.totalPris(pgTest3.getNavn(), varer3);

        assertEquals(200, TC1);
        assertEquals(0, TC2);
        assertEquals(-200, TC3);

    }

    @Test
    void createSpiritus() {
        // Mike

        Controller testcontroller = new Controller();
        //nedre grænse
        Exception exception =assertThrows(IllegalArgumentException.class, ()-> testcontroller.createSpiritus("øl", -15));
        //øvre grænse
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> testcontroller.createSpiritus("øl1", 5000));
        //normalt flow
        //Nedre gyldig grænseværdi
        Drikkevare drikkevare = testcontroller.createSpiritus("l", 1);
        assertEquals(1, drikkevare.getAlkoholProcent());
        //mellem
        Drikkevare drikkevare1 = testcontroller.createSpiritus("p",50);
        assertEquals(50, drikkevare1.getAlkoholProcent());
        //øvre gyldig grænseværdi
        Drikkevare drikkevare2 = testcontroller.createSpiritus("lksda", 99);
        assertEquals(99, drikkevare2.getAlkoholProcent());

    }

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
    void createProduktSalg() {
        Controller testController = new Controller();

        Prisgruppe pgTest = new Prisgruppe(100, "Butik");
        Prisgruppe pgTest2 = new Prisgruppe(100.5, "Butik");
        Prisgruppe pgTest3 = new Prisgruppe(-100, "Butik");
        Drikkevare testvare = new Drikkevare("testvare", 0, Varetype.FLASKE, 0);
        Drikkevare testvare2 = new Drikkevare("Testvare2", 0, Varetype.FLASKE, 0);
        Drikkevare testvare3 = new Drikkevare("Testvare3", 0, Varetype.FLASKE, 0);
        testvare.addPrisgruppe(pgTest);
        testvare2.addPrisgruppe(pgTest2);
        testvare3.addPrisgruppe(pgTest3);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(testvare, 2);
        HashMap<Vare, Integer> varer2 = new HashMap<>();
        varer2.put(testvare2, 0);
        HashMap<Vare, Integer> varer3 = new HashMap<>();
        varer3.put(testvare3, 2);


    }
    // Spørgsmål: Hvorfor har vi beloeb i constructoren, når vi alligevel beregner beløbet i totalPris metoden?
    // Den her metode er et helvede at teste.. GØr det imorgen i skolen.


    @Test
    void createFastRabat(){}
        //Omar

    @Test
    void createProcentRabat() {
        Controller testController = new Controller();
        //Tjekker exceptions
        //Nedre grænse
        Exception exception = assertThrows(IllegalArgumentException.class, () -> testController.createProcentRabat(-11));
        //Øvre grænse.
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> testController.createProcentRabat(5000));

        //gyldige værdier:
        Rabat rabat = testController.createProcentRabat(1);
        Rabat rabat1 = testController.createProcentRabat(50);
        Rabat rabat2 = testController.createProcentRabat(99);
        //nedre gyldig
        // 100 * 1% = 1
        assertEquals(1, rabat.beregnRabat(100));
        //midt gyldig
        //100 * 50% = 50
        assertEquals(50, rabat1);
        //topgyldig.
        //100 * 99% = 99
        assertEquals(99, rabat2);
    }

    @Test
    void createRegning() {
        // Arrange
        Controller c = new Controller();
        Vare testVare = new Drikkevare("testVare", 0, Varetype.FADØL, 0);
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
        assertEquals("Beløb for regning skal være højere end 0.", ex.getMessage());
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> c.createRegning(varer, Betalingsform.REGNING, null, -1, "testNavn1"));
        assertEquals("Beløb for regning skal være højere end 0.", ex1.getMessage());
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
        //assertFalse(u.)
        // TODO: Svær at teste siden flowet ikke er færdiggjort for udlejning så der er lidt hiccups - Mads Bjerg 3/4/2022

        // Assert exceptional arguments
        Exception ex = assertThrows(IllegalArgumentException.class, () -> c.createUdlejning(varer, 100, LocalDate.of(1999, 1,1), LocalDate.of(1999, 1, 1),Betalingsform.KONTANT, null));
        assertEquals("Startdato skal være før slutdato.", ex.getMessage());
        ex = assertThrows(IllegalArgumentException.class, () -> c.createUdlejning(varer, 100, LocalDate.of(1999, 1,2), LocalDate.of(1999, 1, 1),Betalingsform.KONTANT, null));
        assertEquals("Startdato skal være før slutdato.", ex.getMessage());

    }

    @Test
    void getRundvisninger() {
        // Omar
    }

    @Test
    void resetPrisgrupper() {
        // Jens

        // --- Arrange ----
        Controller testController = new Controller();

        Prisgruppe pgTest1 = new Prisgruppe(100, "prisgruppeTest");
        Prisgruppe pgTest2 = new Prisgruppe(150, "prisgruppeTest2");
        Prisgruppe pgTest3 = new Prisgruppe(0, "prisgruppeTestNull");

        Drikkevare test1test = testController.createFlaske("Test1", 0);
        Drikkevare test2test = testController.createFlaske("Test2", 0);
        Drikkevare test3test = testController.createFlaske("Test3", 0);

        test1test.addPrisgruppe(pgTest1);
        test2test.addPrisgruppe(pgTest2);
        test3test.addPrisgruppe(pgTest3);

        // --- Act ----



        // --- Assert ----

       assertEquals(0, TC1);
    }
}