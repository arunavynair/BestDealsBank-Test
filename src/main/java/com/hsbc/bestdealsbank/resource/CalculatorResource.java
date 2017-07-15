package com.hsbc.bestdealsbank.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.hsbc.bestdealsbank.domain.DealDetails;
import com.hsbc.bestdealsbank.service.CalculatorService;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class CalculatorResource {

    private final CalculatorService calculatorService;

    @Inject
    public CalculatorResource(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GET
    @Path("/deal/clients/{id}")
    public Response getAllDealsForClient(@PathParam("clientId") String clientId) {
        return Response.ok(this.calculatorService.getAllDealsForClient(clientId)).build();
    }

    @PUT
    @Path("/deal/clients/{id}")
    public Response putClientDeals(@PathParam("clientId") String clientID, List<DealDetails> deals) {
        this.calculatorService.putClientDeals(clientID, deals);
        return Response.ok().build();
    }

    @POST
    @Path("/calculator")
    public Response calculate(@QueryParam("type") String calculatorType, DealDetails dealDetails) {
        return Response.ok(this.calculatorService.calculate(calculatorType, dealDetails)).build();
    }

}
