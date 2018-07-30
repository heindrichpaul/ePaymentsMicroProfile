package it.com.paul.rest;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountEndpointTest {

    private final String border = "======================================";

    @Test
    public void testGetAccounts() {

        String port = System.getProperty("liberty.test.port");
        String war = System.getProperty("war.name");
        String url = "http://localhost:" + port + "/" + war + "/";

        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);

        WebTarget target = client.target(url + "ePayments/accounts");
        Response response = target.request().get();

        printCaption("Testing GET on empty list");
        assertEquals("Incorrect response code from " + url, Response.Status.OK.getStatusCode(), response.getStatus());

        JsonObject obj = response.readEntity(JsonObject.class);
        assertTrue("Account list is not empty",obj.getJsonArray("accounts").isEmpty());

        printCaption("Testing account creation");
        //Create account
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        String name = "John";
        String lastname = "Doe";
        double amount = 10000.0;
        obj = objBuilder.add("name",name).add("lastname",lastname).add("amount",amount).build();
        response = target.request().put(Entity.json(obj));

        assertEquals("Status code for creation of account does not match expected.",Response.Status.CREATED.getStatusCode(),response.getStatus());
        response = target.request().get();
        obj = response.readEntity(JsonObject.class);
        JsonObject account = (JsonObject) obj.getJsonArray("accounts").get(0);
        long accountNumber = account.getJsonNumber("accountNumber").longValueExact();

        assertEquals("Account list does not have only one account in it.",1,obj.getJsonArray("accounts").size());
        assertEquals("Account owner's name is different from expected.",name+" "+lastname,account.getString("ownerName"));
        assertEquals("Account balance is different from expected.",amount,account.getJsonNumber("balance").doubleValue(),0);
        assertTrue("Account number is not a positive number.",accountNumber > 0L);

        printCaption("Testing empty creation call");
        //Test empty creation put
        obj = objBuilder.build();
        response = target.request().put(Entity.json(obj));
        assertEquals("Status code for rejection for creation of account does not match expected.",Response.Status.PARTIAL_CONTENT.getStatusCode(),response.getStatus());

        printCaption("Testing FAKE account lookup");
        //test fake account lookup
        Long fakeAccountNumber = Math.abs(new Random().nextLong());
        WebTarget nonExistentAccountTarget = client.target(url + "ePayments/accounts/"+fakeAccountNumber);
        response = nonExistentAccountTarget.request().get();
        assertEquals("Status code for look up of non existent account does not match expected.",Response.Status.NOT_FOUND.getStatusCode(),response.getStatus());

        printCaption("Testing valid account lookup");
        //test real account lookup
        WebTarget specificTarget = client.target(url + "ePayments/accounts/"+accountNumber);
        response = specificTarget.request().get();
        obj = response.readEntity(JsonObject.class);
        assertEquals("Status code for look up of non existent account does not match expected.",Response.Status.OK.getStatusCode(),response.getStatus());
        assertEquals("Account number does not match expected.",accountNumber,obj.getJsonNumber("accountNumber").longValueExact());
        assertEquals("Account owner's name is different from expected.",name+" "+lastname,account.getString("ownerName"));
        assertEquals("Account balance is different from expected.",amount,account.getJsonNumber("balance").doubleValue(),0);
        JsonArray transactions = obj.getJsonArray("transactions");
        assertFalse("Account transactions are empty",transactions.isEmpty());
        obj = (JsonObject) transactions.get(0);
        assertEquals("The transaction amount does not match the initial deposit.",amount,obj.getJsonNumber("transactionAmount").doubleValue(),0);
        assertEquals("The transaction type does not match expected","C",obj.getString("transactionType"));


        response.close();
    }

    private void printCaption(String caption){
        System.out.println(border);
        System.out.println(caption);
        System.out.println(border);
    }
}