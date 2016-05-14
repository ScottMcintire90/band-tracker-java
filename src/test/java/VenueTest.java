import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_VenueInstantiatesCorrectly_true() {
    Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
    assertEquals(true, newVenue instanceof Venue);
  }

  @Test
  public void Venue_ReturnsWithName_name() {
    Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
    assertEquals("Crystal Ballroom", newVenue.getName());
  }

  @Test
  public void Venue_ObjectsAreEqual_true() {
    Venue firstVenue = new Venue("Crystal Ballroom", "Portland, OR");
    Venue secondVenue = new Venue("Crystal Ballroom", "Portland, OR");
    assertTrue(firstVenue.equals(secondVenue));
  }


}
