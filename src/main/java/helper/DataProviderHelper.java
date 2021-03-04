package helper;

import org.testng.annotations.DataProvider;


public class DataProviderHelper {

    @DataProvider(name = "title")
    public Object[][] createTitle() {

        return new Object[][]{
                {"Harry Potter"},
                {"Ali"}
        };
    }
}
