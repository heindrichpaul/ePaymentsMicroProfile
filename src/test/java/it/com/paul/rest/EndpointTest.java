package it.com.paul.rest;

public class EndpointTest {

 /*   @Test
    public void testGetProperties() {
        String port = System.getProperty("liberty.test.port");
        String war = System.getProperty("war.name");
        String url = "http://localhost:" + port + "/" + war + "/";

        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);


        WebTarget target = client.target(url + "System/properties");
        Response response = target.request().get();

        assertEquals("Incorrect response code from " + url, Response.Status.OK.getStatusCode(), response.getStatus());

        JsonObject obj = response.readEntity(JsonObject.class);

        assertEquals("The system property for the local and remote JVM should match",
                System.getProperty("os.name"),
                obj.getString("os.name"));
        response.close();
    }
    */
}