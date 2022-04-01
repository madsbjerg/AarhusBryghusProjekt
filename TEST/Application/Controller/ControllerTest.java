package Application.Controller;

import Application.Models.Drikkevare;
import Application.Models.Prisgruppe;
import Application.Models.Vare;
import Application.Models.Varetype;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller = Controller.getController();

    @org.junit.jupiter.api.Test
    void getPrisgrupperByName() {

        System.out.println("Spørg Margrethe");
        assertTrue(true);

    }

    @org.junit.jupiter.api.Test
    void setActivePrisgruppe() {

    }

    @org.junit.jupiter.api.Test
    void resetPrisgrupper() {
    }

    @org.junit.jupiter.api.Test
    void totalPris() {

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

        double TC1 = controller.totalPris(pgTest.getNavn(), varer);
        double TC2 = controller.totalPris(pgTest2.getNavn(),varer2);
        double TC3 = controller.totalPris(pgTest3.getNavn(), varer3);

        assertEquals(200, TC1);
        assertEquals(0, TC2);
        assertEquals(-200, TC3);



    }
}