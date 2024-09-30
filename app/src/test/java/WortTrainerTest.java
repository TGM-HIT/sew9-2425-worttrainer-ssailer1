import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.WortEintrag;
import model.WortTrainer;
import model.WortTrainerSpeichern;

public class WortTrainerTest {

    private WortTrainer wt;
    private WortTrainerSpeichern wts;

    @BeforeEach
    public void setup() {
        this.wt = new WortTrainer();
        this.wts = new WortTrainerSpeichern();
    }

    // 1. Test für WortEintrag Konstruktor und Getter-Methoden
    @Test
    public void testWortEintragConstructorAndGetters() {
        WortEintrag eintrag = new WortEintrag("TestName", "TestUrl");
        assertEquals("TestName", eintrag.getName());
        assertEquals("TestUrl", eintrag.getUrl());
    }

    /**
     * Testet ob ein Eintrag erstellt wurde
     */
    @Test
    public void testAddEintrag() {
        int temp = 1 + wt.getList().size();
        WortEintrag eintrag = new WortEintrag("TestName", "TestUrl");
        wt.addEintrag(eintrag.getName(), eintrag.getUrl());

        assertEquals(temp, wt.getList().size());
        assertEquals("TestName", wt.getList().get(wt.getList().size()-1).getName());
    }

    /**
     * Testen der Check Methode
     */

    @Test
    public void testCheck(){
        this.wt.addEintrag("Hund", "Hund");
        assertEquals(this.wt.getList().get(wt.getList().size()-1).getName(), "Hund");
    }

    /**
     * Testen ob sich die Stats richtig erhöhen
     */
    @Test 
    public void rightStats(){
        this.wts.addTrue();
        this.wts.addFalse();

        assertEquals(this.wts.getVersuche(), 2);
        assertEquals(this.wts.getRVersuche(), 1);
    }

    /**
     * Checken ob die richtige Anzahl an Wörtern geladen wird
     */
    @Test
    public void loadCheck(){
        assertEquals(this.wt.getList().size(),6);
    }

}
