package com.paul.rest;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.paul.rest.RestApplication.accounts;

@Path("transfer")
public class TransactionResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transferFunds(JsonObject payload) {
        if (!(payload.containsKey("debitedAccount") || payload.containsKey("creditedAccount") || payload.containsKey("transferAmount"))) {
            return Response.noContent().status(Response.Status.PARTIAL_CONTENT).build();
        }
        Long debitedAccountNumber = payload.getJsonNumber("debitedAccount").longValueExact();
        Long creditedAccountNumber = payload.getJsonNumber("creditedAccount").longValueExact();
        double transferAmount = payload.getJsonNumber("transferAmount").doubleValue();
        if (!accounts.containsKey(debitedAccountNumber) && !accounts.containsKey(creditedAccountNumber)) {
            return Response.noContent().status(522).build();
        } else if (!accounts.containsKey(debitedAccountNumber)) {
            return Response.noContent().status(520).build();
        } else if (!accounts.containsKey(creditedAccountNumber)) {
            return Response.noContent().status(521).build();
        } else {
            if (accounts.get(debitedAccountNumber).transferAmountToAccount(transferAmount, accounts.get(creditedAccountNumber))) {
                return Response.noContent().status(Response.Status.ACCEPTED).build();
            } else {
                return Response.noContent().status(530).build();
            }
        }

    }
}
