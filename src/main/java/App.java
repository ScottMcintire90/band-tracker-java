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
      String name = request.queryParams("name");
      String genre = request.queryParams("genre");
      if(name.equals("") || genre.equals("")) {
        response.redirect("http://localhost:4567/");
        return null;
      }
      Band newBand = new Band(name, genre);
      newBand.save();

      String venueName = request.queryParams("venueName");
      String location = request.queryParams("location");
      // if(venueName.equals("")) {
      //   response.redirect("http://localhost:4567/");
      //   return null;
      // }
      Venue newVenue = new Venue(venueName, location);
      newVenue.save();

      response.redirect("/");
      return null;
    });

    get("/bands/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", newBand);
      model.put("venues", Venue.all());
      model.put("bandVenues", newBand.getVenues());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params(":id")));
      int venueId = Integer.parseInt(request.queryParams("venue_id"));
      Venue newVenue = Venue.find(venueId);
      newBand.addVenue(newVenue);
      response.redirect("/bands/" +newBand.getId());
      return null;
    });

    get("/venues/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue newVenue = Venue.find(Integer.parseInt(request.params(":id")));
      model.put("venue", newVenue);
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/genres", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      if(request.queryParams("searchBand") != null) {
        String searchBand = request.queryParams("searchBand");
        List<Band> foundBands = Band.searchGenre("%" + searchBand + "" + "%");
        model.put("foundBands", foundBands);
      }
      model.put("template", "templates/bands-search.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/locations", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      if(request.queryParams("searchVenue") != null) {
        String searchVenue = request.queryParams("searchVenue");
        List<Venue> foundVenues = Venue.searchLocation("%" + searchVenue + "" + "%");
        model.put("foundVenues", foundVenues);
      }
      model.put("template", "templates/venues-search.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params(":id")));
      newBand.delete();
      response.redirect("/");
      return null;
    });

    get("/venues/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue newVenue = Venue.find(Integer.parseInt(request.params(":id")));
      newVenue.delete();
      response.redirect("/");
      return null;
    });

    get("/bands/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", newBand);
      model.put("template", "templates/band-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");
      String genre = request.queryParams("genre");
      if(name != null && genre != null) {
        newBand.update(name, genre);
      }
      model.put("band", newBand);
      response.redirect("/");
      return null;
    });

    get("/venues/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue newVenue = Venue.find(Integer.parseInt(request.params(":id")));
      model.put("venue", newVenue);
      model.put("template", "templates/venue-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue newVenue = Venue.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");
      String location = request.queryParams("location");
      if(name != null && location != null) {
        newVenue.update(name, location);
      }
      model.put("venue", newVenue);
      response.redirect("/");
      return null;
    });
  }
}
