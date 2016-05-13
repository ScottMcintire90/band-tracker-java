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
        .addParameter("name", name)
        .addParameter("genre", genre)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Band> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands";
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
}
