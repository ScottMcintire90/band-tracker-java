import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/new-band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/new-venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String genre = request.queryParams("genre");
      if(name != null && genre != null) {
        Band newBand = new Band(name, genre);
        newBand.save();
      }
      String venueName = request.queryParams("venueName");
      String location = request.queryParams("location");
      if (venueName != null && location != null) {
        Venue newVenue = new Venue(venueName, location);
        newVenue.save();
      }
      response.redirect("/");
      return null;
    });
    
    // get("/bands/genres", (request, response) -> {
    //   if (request.queryParams("searchBand") != null) {
    //     String searchBand = request.queryParams("searchBand");
    //     List<Band> foundBands = Band.searchGenre("%" + searchBand + "" + "%");
    //     model.put("foundBands", foundBands);
    //   }
    //   model.put("template", "templates/band-search.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
  }
}
