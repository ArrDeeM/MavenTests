import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GetContent extends BaseClass{
    CloseableHttpClient client;
    CloseableHttpResponse response;
    @BeforeMethod
    public void setup(){
        client = HttpClientBuilder.create().build();
    }
    @AfterMethod
    public void closeResources() throws IOException {
        client.close();
        response.close();
    }
    @DataProvider
    private Object[][]endpoints(){
        return new Object[][]{
                {""},
                {"/rate_limit"},
                {"/search/repositories?q=java"}
        };
    }
    @Test
    public void contentOfResponse() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/users/arrdeem");
        response = client.execute(get);
        //Use apache entity not JDK entity
        String jsonBody = EntityUtils.toString(response.getEntity());
        System.out.println(jsonBody);
    }
    @Test
    public void returnsCorrectLogin() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/users/arrdeem");
        response = client.execute(get);
        String jsonBody = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = new JSONObject(jsonBody);
        String loginValue = (String) getValueFor(jsonObject, "login");
        Assert.assertEquals(loginValue,"ArrDeeM");
    }
    private Object getValueFor(JSONObject jsonObject, String key){
        return jsonObject.get(key);
    }
}
