package com.pik.backend;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.assertj.core.util.Lists;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.util.List;

public class MobileClientSimulation {

    private static List<String> points = Lists.newArrayList(
            "%s 1557415405000 51.7917863 19.3694545 0.0",
            "%s 1557415407000 51.7917887 19.3695295 0.0",
            "%s 1557415409000 51.7918213 19.3696299 0.0",
            "%s 1557415411000 51.7918202 19.3697103 0.0",
            "%s 1557415413000 51.7918234 19.3698169 0.0",
            "%s 1557415415000 51.7918309 19.3699007 0.0",
            "%s 1557415417000 51.791858 19.3699744 0.0",
            "%s 1557415419000 51.7919107 19.3700037 0.0",
            "%s 1557415421000 51.7919626 19.3700192 0.0",
            "%s 1557415426000 51.7921004 19.3700411 0.0"
    );

    @Test
    @Ignore
    public void shouldStartRouteInsertAllPointsAndEndRoute() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost startRoute = new HttpPost(new URI("http://127.0.0.1:8080/routes/create/1"));
        CloseableHttpResponse response = client.execute(startRoute);
        HttpEntity entity = response.getEntity();
        int routeId = new JSONObject(entity.toString()).getInt("routeId");
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        for (String point : points) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/routes/write");
            httpPost.setEntity(new StringEntity(point));
            Assert.assertEquals(200, client.execute(httpPost).getStatusLine().getStatusCode());
        }

    }

}
