import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertNotNull;

public class OmdbApiTest {
    public static String errorMessage = "No API key provided";

    @Test
    public void shouldSearchByTitleAndYear(){
        Response response = RestAssured.given()
                .queryParam("t", "Harry Potter")
                .queryParam("y", 2011)
                .queryParam("apiKey", "52cd129d")
                .get("http://www.omdbapi.com")
                .then()
                .statusCode(200)
                .extract().response();

        response.prettyPeek();
        assertThat(response.getBody().jsonPath().getString("Title"), Matchers.containsString("Harry"));
    }

    @Test
    public void shouldSearchByImdbId(){
        Response byIdOrTitle = RestAssured.given()
                .queryParam("t", "Harry Potter")
                .queryParam("y", 2011)
                .queryParam("apiKey", "52cd129d")
                .get("http://www.omdbapi.com")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String imdbID = byIdOrTitle.getBody().jsonPath().getString("imdbID");
        assertNotNull(imdbID);

        Response bySearch = RestAssured.given()
                .queryParam("i", imdbID)
                .queryParam("apiKey", "52cd129d")
                .get("http://www.omdbapi.com")
                .then()
                .extract().response();

        assertThat(bySearch.getStatusCode(),Matchers.is(200));
        assertThat(byIdOrTitle.getBody().jsonPath().getString("Title"),Matchers.is(bySearch.getBody().jsonPath().getString("Title")));
    }

    @Test
    public void shouldNotGetResponseWithoutApiKey(){
        Response response = RestAssured.given()
                .queryParam("t", "Harry Potter")
                .get("http://www.omdbapi.com")
                .then()
                .statusCode(401)
                .extract().response();

        assertThat(response.getBody().jsonPath().getString("Error"), Matchers.containsString(errorMessage));
    }
}
