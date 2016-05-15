import java.util.*;
import org.sql2o.*;

public class Band {
  private String name;
  private String genre;
  private int id;

  public Band(String name, String genre) {
    this.name = name;
    this.genre = genre;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getGenre() {
    return genre;
  }

  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) obj;
      return this.getId() == newBand.getId() &&
      this.getName().equals(newBand.getName()) &&
      this.getGenre().equals(newBand.getGenre());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name, genre) VALUES (:name, :genre)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("genre", this.genre)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Band> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands ORDER BY name ASC";
      List<Band> all = con.createQuery(sql)
        .executeAndFetch(Band.class);
      return all;
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public void update(String newName, String newGenre) {
    if(newName.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE bands SET name = :name WHERE id=:id";
        con.createQuery(sql)
          .addParameter("name", newName)
          .addParameter("id", id)
          .executeUpdate();
      }
    }
    if(newGenre.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE bands SET genre = :genre WHERE id=:id";
        con.createQuery(sql)
          .addParameter("genre", newGenre)
          .addParameter("id", id)
          .executeUpdate();
      }
    }
  }

  public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id);";
      con.createQuery(sql)
        .addParameter("venue_id", venue.getId())
        .addParameter("band_id",this.getId())
        .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT venue_id FROM bands_venues WHERE band_id = :band_id;";
      List<Integer> venueIds = con.createQuery(sql)
      .addParameter("band_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Venue> venues = new ArrayList<Venue>();

      for (Integer venueId : venueIds) {
        String bandQuery = "SELECT * FROM venues WHERE id = :venueId;";
        Venue venue = con.createQuery(bandQuery)
        .addParameter("venueId", venueId)
        .executeAndFetchFirst(Venue.class);
        venues.add(venue);
      }
      return venues;
    }
  }

  public static List<Band> searchGenre(String input) {
    try(Connection con = DB. sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE genre LIKE :input";
      return con.createQuery(sql)
        .addParameter("input", input)
        .executeAndFetch(Band.class);
    }
  }
}
