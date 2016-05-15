import java.util.*;
import org.sql2o.*;

public class Venue {
  private int id;
  private String name;
  private String location;

  public Venue(String name, String location) {
    this.name = name;
    this.location = location;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) obj;
      return this.getId() == newVenue.getId() &&
      this.getName().equals(newVenue.getName()) &&
      this.getLocation().equals(newVenue.getLocation());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (name, location) VALUES (:name, :location)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("location", this.location)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Venue> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues ORDER BY name ASC";
      List<Venue> all = con.createQuery(sql)
        .executeAndFetch(Venue.class);
        return all;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM venues WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
    }
  }

  public void update(String newName, String newLocation) {
    if(newName.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE venues SET name = :name WHERE id=:id";
        con.createQuery(sql)
          .addParameter("name", newName)
          .addParameter("id", id)
          .executeUpdate();
      }
    }
    if(newLocation.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE venues SET location = :location WHERE id=:id";
        con.createQuery(sql)
          .addParameter("location", newLocation)
          .addParameter("id", id)
          .executeUpdate();
      }
    }
  }

  public static List<Venue> searchLocation(String input) {
    try(Connection con = DB. sql2o.open()) {
      String sql = "SELECT * FROM venues WHERE location LIKE :input";
      return con.createQuery(sql)
        .addParameter("input", input)
        .executeAndFetch(Venue.class);
    }
  }
}
