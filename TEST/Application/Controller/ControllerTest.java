package Application.Controller;

import Application.Models.Drikkevare;
import Application.Models.Prisgruppe;
import Application.Models.Vare;
import Application.Models.Varetype;
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
    }

    @org.junit.jupiter.api.Test
    void resetPrisgrupper() {
    }

    @org.junit.jupiter.api.Test
    void totalPris() {

        Controller testController = new Controller();

        // TODO: Exception (valid operation/ IllegalState) i controller. Modificer TC3 til at give Exception.

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
}