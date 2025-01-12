package uk.ac.nott.cs.comp2013.frogger;

import com.pholser.junit.quickcheck.Property;
import javafx.application.Platform;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.*;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class MainTests {

    private Main mainApplication;
    @Property
    public void testConstructor() throws Exception {
        Main mainApplication = new Main();
        assertNotNull(mainApplication);
    }

    @Property
    public void testStart() throws Exception {
        Platform.startup(() ->{
            Stage stage = new Stage();
            Main mainApplication = new Main();

            try {
                mainApplication.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertTrue(stage.isShowing());
        });
    }

}