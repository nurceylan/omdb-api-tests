import helper.DataProviderHelper;
import helper.MessageResource;
import io.restassured.response.Response;
import model.GetResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import spec.ResponseSpec;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertNotNull;

public class OmdbApiTest extends BaseServiceTest {

    @Test(dataProvider = "title",dataProviderClass = DataProviderHelper.class)
    public void shouldSearchByTitleAndYear(String title) {
        Map<String, Object> titleAndYear = requestMaps.titleAndYearMap(title);

        Response response = byIdOrTitleService.get(titleAndYear, ResponseSpec.checkStatusCodeOk());
        GetResponse as = response.as(GetResponse.class);

        response.prettyPeek();
        assertThat(as.getTitle(), Matchers.containsString(title));
    }

    @Test
    public void shouldSearchByImdbId() {
        Map<String, Object> titleAndYear = requestMaps.titleAndYearMap("Harry Potter");

        Response byIdOrTitle = byIdOrTitleService.get(titleAndYear, ResponseSpec.checkStatusCodeOk());

        String imdbID = byIdOrTitle.getBody().jsonPath().getString("imdbID");
        assertNotNull(imdbID);

        Map<String, Object> bySearchParams = requestMaps.imdbIdMap(imdbID);

        Response bySearch = byIdOrTitleService.get(bySearchParams, ResponseSpec.checkStatusCodeOk());

        assertThat(bySearch.getStatusCode(), Matchers.is(200));
        assertThat(byIdOrTitle.getBody().jsonPath().getString("Title"), Matchers.is(bySearch.getBody().jsonPath().getString("Title")));
    }

    @Test
    public void shouldNotGetResponseWithoutApiKey() {
        Map<String, Object> titleMap = requestMaps.titleMap();
        Response response = byIdOrTitleService.get(titleMap, ResponseSpec.checkStatusCodeUnauthorized());

        String message = MessageResource.getMessage("no.api.key");
        assertThat(response.getBody().jsonPath().getString("Error"), Matchers.containsString(message));
    }
}
