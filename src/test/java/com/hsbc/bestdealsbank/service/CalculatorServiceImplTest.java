package com.hsbc.bestdealsbank.service;

import java.util.Collections;
import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsbc.bestdealsbank.bootstrap.ApplicationModule;
import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.DealDetails;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(JukitoRunner.class)
@UseModules({ ApplicationModule.class })
public class CalculatorServiceImplTest {

    @Test
    public void shouldCalculateReturnPass(CalculatorService underTest) throws Exception {

        DealDetails dealDetails = testData();

        CalculatorResponse calculatorResponse = underTest.calculate("simple", dealDetails);

        assertThat(String.format("%.2f", calculatorResponse.getAmount()), is("318.55"));
        assertThat(String.format("%.2f", calculatorResponse.getConverted()), is("411.82"));
    }

    @Test
    public void shouldGetAllDealsForAllClients(CalculatorService underTest) throws Exception {
        DealDetails testData = testData();
        List<DealDetails> deals = Collections.singletonList(testData);
        
        underTest.putClientDeals("5", deals);
        underTest.putClientDeals("5", deals);
        underTest.putClientDeals("6", deals);
        underTest.putClientDeals("7", deals);
        
        List<CalculatorResponse> allDealsForClient5 = underTest.getAllDealsForClient("5");
        List<CalculatorResponse> allDealsForClient6 = underTest.getAllDealsForClient("6");
        List<CalculatorResponse> allDealsForClient7 = underTest.getAllDealsForClient("7");
        
        assertNotNull(allDealsForClient5);
        assertThat(allDealsForClient5.size(), is(4));
        
        assertNotNull(allDealsForClient6);
        assertThat(allDealsForClient6.size(), is(2));
        
        assertNotNull(allDealsForClient7);
        assertThat(allDealsForClient7.size(), is(2));
    }

    @Test
    public void shouldPutSingleClientDeal(CalculatorService underTest, ObjectMapper mapper) throws Exception {
        
        DealDetails testData = testData();
        List<DealDetails> deals = Collections.singletonList(testData);
        
        underTest.putClientDeals("someid2", deals);
        
        List<CalculatorResponse> allDealsForClient = underTest.getAllDealsForClient("someid2");
        
        assertNotNull(allDealsForClient);
        assertThat(allDealsForClient.size(), is(2));
    }
    
    @Test
    public void shouldPutClientDealsPass(CalculatorService underTest, ObjectMapper mapper) throws Exception {
        
        DealDetails testData = testData();
        List<DealDetails> deals = Collections.singletonList(testData);
        
        underTest.putClientDeals("someid1", deals);
        underTest.putClientDeals("someid1", deals);
        
        List<CalculatorResponse> allDealsForClient = underTest.getAllDealsForClient("someid1");
        
        assertNotNull(allDealsForClient);
        assertThat(allDealsForClient.size(), is(4));
    }

    private DealDetails testData() {
        double principle = 2000;
        double noOfYears = 5;
        double rate = 3;
        DealDetails dealDetails = new DealDetails(principle, noOfYears, rate);
        return dealDetails;
    }

}
