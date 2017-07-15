package com.hsbc.bestdealsbank.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;
import com.google.inject.util.Providers;
import com.hsbc.bestdealsbank.bootstrap.ApplicationModule;
import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.DealDetails;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CalculatorResourceTest {

    private static final int PORT = 7001;
    DefaultClientConfig config = new DefaultClientConfig();
    Provider<HttpServer> server;

    @Before
    public void start() throws Exception {

        ResourceConfig resourceConfig = new PackagesResourceConfig("com.hsbc");
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Injector injector = Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                install(new ApplicationModule());
            }
        });

        IoCComponentProviderFactory resourceProvider = new GuiceComponentProviderFactory(resourceConfig, injector);
        String url = UriBuilder.fromUri("http://localhost/").port(PORT).build() + "services/";
        server = Providers.of(GrizzlyServerFactory.createHttpServer(url, resourceConfig, resourceProvider));

        assertTrue(server.get().isStarted());
    }

    @After
    public void stop() {
        server.get().stop();
        assertFalse(server.get().isStarted());
    }

    @Test
    public void shouldGetErrorForInvalidCalculatorType() throws IOException {

        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://localhost/").port(7001).build());

        DealDetails dealDetails = getTestDealDetails();
        String inputJson = new ObjectMapper().writeValueAsString(dealDetails);
        
        //when
        ClientResponse clientResponse = service.path("services/calculator")
                        .queryParam("type", "INVALID-TYPE")
                        .type(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class, inputJson);
        //then
        assertThat(clientResponse.getStatus(), is(500));
    }
    
    @Test
    public void shouldGetSuccessWithAllClientDeals() throws IOException {

        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://localhost/").port(7001).build());

        DealDetails deal = getTestDealDetails();
        List<DealDetails> deals = new ArrayList<>();
        deals.add(deal);
        String inputJson = new ObjectMapper().writeValueAsString(deals);
        
        //given
        ClientResponse putResponse = service.path("services/deal/clients/client-1")
                        .type(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .put(ClientResponse.class, inputJson);
        assertTrue(putResponse.getStatus() == 200);
        
        //when
        ClientResponse clientResponse = service.path("services/deal/clients/client-1")
                        .type(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);

        //then
        List<?> entity = clientResponse.getEntity(List.class);

        assertTrue(clientResponse.getStatus() == 200);
        assertFalse(entity.isEmpty());
        assertThat(entity.size(), is(2));
    }
    
    @Test
    public void shouldGetSuccessEvenIfNoDealFoundForAClient() throws IOException {

        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://localhost/").port(7001).build());

        ClientResponse clientResponse = service.path("services/deal/clients/client-1")
                        .type(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);

        assertTrue(clientResponse.getStatus() == 200);
    }

    @Test
    public void shouldCalculateSimpleInterestDealDetails() throws IOException {

        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://localhost/").port(7001).build());
        DealDetails dealDetails = getTestDealDetails();

        String inputJson = new ObjectMapper().writeValueAsString(dealDetails);

        ClientResponse clientResponse = service.path("services/calculator")
                        .queryParam("type", "simple")
                        .type(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class, inputJson);

        assertTrue(clientResponse.getStatus() == 200);

        CalculatorResponse calculatorResponse = clientResponse.getEntity(CalculatorResponse.class);

        assertThat(String.format("%.2f", calculatorResponse.getAmount()), is("318.55"));
        assertThat(String.format("%.2f", calculatorResponse.getConverted()), is("411.82"));
    }

    @Test
    public void shouldCalculateCompoundInterestDealDetails() throws IOException {

        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://localhost/").port(7001).build());

        DealDetails dealDetails = getTestDealDetails();
        String inputJson = new ObjectMapper().writeValueAsString(dealDetails);

        ClientResponse clientResponse = service.path("services/calculator")
                        .queryParam("type", "compound")
                        .type(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class, inputJson);

        assertTrue(clientResponse.getStatus() == 200);

        CalculatorResponse calculatorResponse = clientResponse.getEntity(CalculatorResponse.class);

        assertThat(String.format("%.2f", calculatorResponse.getAmount()), is("300.00"));
        assertThat(String.format("%.2f", calculatorResponse.getConverted()), is("387.84"));
    }

    @Test
    public void shouldReturnValidJsonResponse() throws IOException {

        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://localhost/").port(7001).build());
        DealDetails dealDetails = getTestDealDetails();
        String inputJson = new ObjectMapper().writeValueAsString(dealDetails);

        ClientResponse clientResponse = service.path("services/calculator")
                        .queryParam("type", "simple")
                        .type(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class, inputJson);

        CalculatorResponse calculatorResponse = clientResponse.getEntity(CalculatorResponse.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(calculatorResponse);

        assertNotNull(objectMapper.readValue(responseJson.getBytes(), CalculatorResponse.class));
    }

    private DealDetails getTestDealDetails() {
        double principle = 2000;
        double noOfYears = 5;
        double rate = 3;
        DealDetails dealDetails = new DealDetails(principle, noOfYears, rate);
        return dealDetails;
    }
}
