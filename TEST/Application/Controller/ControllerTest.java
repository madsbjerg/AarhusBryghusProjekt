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
    // Spørgsmål: Hvorfor har vi beloeb i constructoren, når vi alligevel beregner beløbet i totalPris metoden?
    // Den her metode er et helvede at teste.. GØr det imorgen i skolen.


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
    void createProduktSalgTestPåVarer(){
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
    void createFastRabat(){}
    


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
        //
    }

    @Test
    void createRundvisning() {
        Controller c = new Controller();
        Rundvisning rundvisning = new Rundvisning("Mads", Varetype.RUNDVISNING, 4, LocalDateTime.of(2022, 4, 4,12, 10 ));
        Prisgruppe prisgruppe = new Prisgruppe(400, "Butik");

        rundvisning.addPrisgruppe(prisgruppe);

        HashMap<Vare, Integer> varer = new HashMap<>();
        varer.put(rundvisning, 1);

        //act
        Rundvisning r = c.createRundvisning("Mads", 4, LocalDateTime.of(2022, 4, 8, 12, 10));



        // Assert exceptional arguments
        Exception ex = assertThrows(IllegalArgumentException.class, () -> c.createRundvisning("Mads", 1, LocalDateTime.of(2022, 4, 25, 12, 10)));
        assertEquals("Tidspunkt er efter 14 dage af oprettelse af rundvisning.", ex.getMessage());


    }

    @Test
    void testResetPrisgrupper() {
        // Jens
    }
}