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
        .addParameter("name", name)
        .addParameter("location", location)
        .executeUpdate()
        .getKey();
    }
  }


  public static List<Venue> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues";
      List<Venue> all = con.createQuery(sql)
        .executeAndFetch(Venue.class);
        return all;
    }
  }
}
