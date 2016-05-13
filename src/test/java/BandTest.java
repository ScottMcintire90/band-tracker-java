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

  @Test
  public void save_savesBandToDatabase_true() {
    Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
    newBand.save();
    assertTrue(newBand.all().size() == 1);
  }

  @Test
  public void find_findsBandById_true() {
    Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
    newBand.save();
    Band foundBand = Band.find(newBand.getId());
    assertTrue(foundBand.getName().equals(newBand.getName()));
  }

  @Test
  public void delete_deletesBandFromDB_true() {
    Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
    newBand.save();
    newBand.delete();
    assertTrue(newBand.all().size() == 0);
  }

  @Test
  public void update_updateBandName_name() {
    Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
    newBand.save();
    newBand.update("The Beatles", "");
    assertEquals("The Beatles", Band.find(newBand.getId()).getName());
  }
}
