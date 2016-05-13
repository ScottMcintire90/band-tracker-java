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

 // @Test
 // public void rootTest() {
 //   goTo("http://localhost:4567/");
 //   assertThat(pageSource()).contains("Add a recipe");
 // }
 //
 // @Test
 // public void displayRecipe() {
 //   Recipe newRecipe = new Recipe("4", "Scotts Tots", "","");
 //   newRecipe.save();
 //   String path = String.format("http://localhost:4567/recipe/%d", newRecipe.getId());
 //   goTo(path);
 //   assertThat(pageSource()).contains("Scotts Tots");
 // }
 //
 // @Test
 // public void deleteRecipe() {
 //   Recipe newRecipe = new Recipe("4", "Scotts Tots", "","");
 //   newRecipe.save();
 //   newRecipe.delete();
 //   String path = String.format("http://localhost:4567");
 //   goTo(path);
 //   assertThat(pageSource()).doesNotContain("Scotts Tots");
 // }
 //
 // @Test
 // public void updateRecipe() {
 //   Recipe newRecipe = new Recipe("4", "Scotts Tots", "","");
 //   newRecipe.save();
 //   newRecipe.update("5", "Todds Tots", "", "");
 //   String path = String.format("http://localhost:4567/");
 //   goTo(path);
 //   assertThat(pageSource()).contains("Todds Tots");
 // }
 //
 // @Test
 // public void searchForIngredient() {
 //   Recipe firstRecipe = new Recipe("4", "Scotts Tots", "beef, cheese","");
 //   firstRecipe.save();
 //   Recipe secondRecipe = new Recipe("3", "Vincents Pots", "eggs, apples","");
 //   secondRecipe.save();
 //   Recipe.getRecipeWithIngredient("beef");
 //   String path = String.format("http://localhost:4567/?search=beef");
 //   goTo(path);
 //   assertThat(pageSource()).contains("searchResult");
 // }
}
