package com.hsbc.bestdealsbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hsbc.bestdealsbank.domain.DealDetails;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class InMemoryDaoImplTest {

    public static class TestModule extends org.jukito.TestModule {

        @Override
        protected void configureTest() {
            install(new DaoModule());
        }
    }

    @Test
    public void shouldConvertWithUsd(InMemoryDaoImpl underTest) throws Exception {
        Double convert = underTest.convert("USD", Double.valueOf(1000));
        assertThat(convert, is(1292.8));
    }

    @Test
    public void shouldConvertWithUnknownCurrency(InMemoryDaoImpl underTest) throws Exception {
        Double convert = underTest.convert("YYY", Double.valueOf(1000));
        assertNull(convert);
    }
    
    @Test
    public void shouldPutClientDeals(InMemoryDaoImpl underTest) throws Exception {
        String clientID = saveTestData("1", underTest);
        
        List<DealDetails> dealsForClient = underTest.getAllDealsForClient(clientID);
        assertNotNull(dealsForClient);
        assertTrue(dealsForClient.size()==3);
        
        assertThat(dealsForClient.get(0).getPrinciple(), is(2000.0));
        assertThat(dealsForClient.get(1).getPrinciple(), is(4000.0));
        assertThat(dealsForClient.get(2).getPrinciple(), is(8000.0));
        
        assertThat(dealsForClient.get(2).getNoOfYears(), is(3.0));
        assertThat(dealsForClient.get(1).getNoOfYears(), is(2.0));
        assertThat(dealsForClient.get(0).getNoOfYears(), is(1.0));
        
        assertThat(dealsForClient.get(0).getRate(), is(2.5));
        assertThat(dealsForClient.get(1).getRate(), is(3.5));
        assertThat(dealsForClient.get(2).getRate(), is(4.5));
    }
    @Test
    public void shouldGetClientDeals(InMemoryDaoImpl underTest) throws Exception {
        String clientID = saveTestData("2", underTest);
        
        List<DealDetails> dealsForClient = underTest.getAllDealsForClient(clientID);
        assertNotNull(dealsForClient);
        assertTrue(dealsForClient.size()==3);
        
        assertThat(dealsForClient.get(0).getPrinciple(), is(2000.0));
        assertThat(dealsForClient.get(1).getPrinciple(), is(4000.0));
        assertThat(dealsForClient.get(2).getPrinciple(), is(8000.0));
        
        assertThat(dealsForClient.get(2).getNoOfYears(), is(3.0));
        assertThat(dealsForClient.get(1).getNoOfYears(), is(2.0));
        assertThat(dealsForClient.get(0).getNoOfYears(), is(1.0));
        
        assertThat(dealsForClient.get(0).getRate(), is(2.5));
        assertThat(dealsForClient.get(1).getRate(), is(3.5));
        assertThat(dealsForClient.get(2).getRate(), is(4.5));
    }

    private String saveTestData(String clientID, InMemoryDaoImpl underTest) {
        List<DealDetails> deals = new ArrayList<>();
        deals.add(new DealDetails(2000, 1, 2.5));
        deals.add(new DealDetails(4000, 2, 3.5));
        deals.add(new DealDetails(8000, 3, 4.5));
        
        underTest.putClientDeals(clientID, deals);
        return clientID;
    }

}
