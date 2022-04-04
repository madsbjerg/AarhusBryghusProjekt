package Application.Controller;

import Application.Models.*;
import Storage.Storage;
import org.junit.jupiter.api.Test;

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
        // Mads
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
    void createFastRabat(){

    }
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
        // Mads
    }

    @Test
    void createUdlejning() {
        // Mads
    }

    @Test
    void getRundvisninger() {
        // Omar
    }

    @Test
    void testResetPrisgrupper() {
        // Jens
    }
}