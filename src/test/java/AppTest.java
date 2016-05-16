import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.sql2o.*;
import org.junit.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

 @ClassRule
 public static ServerRule server = new ServerRule();

 @Test
 public void rootTest() {
   goTo("http://localhost:4567/");
   assertThat(pageSource()).contains("Band Tracker");
 }

 @Test
 public void displayBand() {
   Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
   newBand.save();
   String path = String.format("http://localhost:4567/bands/%d", newBand.getId());
   goTo(path);
   assertThat(pageSource()).contains("Pink Floyd");
 }

 @Test
 public void displayVenue() {
   Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
   newVenue.save();
   String path = String.format("http://localhost:4567/venues/%d", newVenue.getId());
   goTo(path);
   assertThat(pageSource()).contains("Crystal Ballroom");
 }

 @Test
 public void deleteBand() {
   Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
   newBand.save();
   newBand.delete();
   String path = String.format("http://localhost:4567");
   goTo(path);
   assertThat(pageSource()).doesNotContain("Scotts Tots");
 }

 @Test
 public void deleteVenue() {
   Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
   newVenue.save();
   newVenue.delete();
   String path = String.format("http://localhost:4567");
   goTo(path);
   assertThat(pageSource()).doesNotContain("Crystal Ballroom");
 }

 @Test
 public void updateBand() {
   Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
   newBand.save();
   newBand.update("The Beatles", "Rock");
   String path = String.format("http://localhost:4567/");
   goTo(path);
   assertThat(pageSource()).contains("The Beatles");
 }

 @Test
 public void updateVenue() {
   Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
   newVenue.save();
   newVenue.update("Keller Auditorium", "Portland, OR");
   String path = String.format("http://localhost:4567/");
   goTo(path);
   assertThat(pageSource()).contains("Keller Auditorium");
 }

 @Test
 public void searchForBands() {
   Band newBand = new Band("Pink Floyd", "Psychedelic Rock");
   newBand.save();
   Band secondBand = new Band("Nsync", "Pop");
   secondBand.save();
   Band.searchGenre("Pop");
   String path = String.format("http://localhost:4567/genres?searchBand=Pop");
   goTo(path);
   assertThat(pageSource()).contains("Nsync");
 }

 @Test
 public void searchForVenues() {
   Venue newVenue = new Venue("Crystal Ballroom", "Portland, OR");
   newVenue.save();
   Venue secondVenue = new Venue("Keller Auditorium", "Portland, OR");
   secondVenue.save();
   Venue.searchLocation("Portland, OR");
   String path = String.format("http://localhost:4567/locations?searchVenue=Portland");
   goTo(path);
   assertThat(pageSource()).contains("Keller Auditorium");
 }
}
