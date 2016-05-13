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
}
