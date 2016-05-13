import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_bandInstantiatesCorrectly_true() {
    Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
    assertEquals(true, newBand instanceof Band);
  }

  @Test
  public void Band_bandInstantiatesWithName_Name() {
  Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
  assertEquals("Pink Floyd", newBand.getName());
  }

  @Test
  public void Band_twoBandsNamesAreEqual_true() {
    Band firstBand = new Band("Pink Floyd", "Psychedelic Rock");
    Band secondBand = new Band("Pink Floyd", "Psychedelic Rock");
    assertTrue(firstBand.equals(secondBand));
  }
}
