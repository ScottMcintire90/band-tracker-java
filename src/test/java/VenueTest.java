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

  @Test
  public void Save_VenueSavesToDatabase_true() {
    Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
    newVenue.save();
    assertTrue(newVenue.all().size() == 1);
  }

  @Test
  public void Delete_VenueIsDeleted_true() {
    Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
    newVenue.save();
    newVenue.delete();
    assertTrue(newVenue.all().size() == 0);
  }

  @Test
  public void find_findsVenueById_true() {
    Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
    newVenue.save();
    Venue foundVenue = Venue.find(newVenue.getId());
    assertTrue(foundVenue.getName().equals(newVenue.getName()));
  }


  @Test
  public void update_updateBandName_name() {
    Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
    newVenue.save();
    newVenue.update("", "Denver, CO");
    assertEquals("Denver, CO", Venue.find(newVenue.getId()).getLocation());
  }
}
