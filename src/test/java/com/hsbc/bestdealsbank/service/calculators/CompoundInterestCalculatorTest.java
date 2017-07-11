package com.hsbc.bestdealsbank.service.calculators;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hsbc.bestdealsbank.bootstrap.ApplicationModule;
import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.DealDetails;
import com.hsbc.bestdealsbank.service.calculators.CompoundInterestCalculator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JukitoRunner.class)
public class CompoundInterestCalculatorTest {

    public static class TestModule extends org.jukito.TestModule {

        @Override
        protected void configureTest() {
            install(new ApplicationModule());
        }
    }

    @Inject
    CompoundInterestCalculator underTest;

    @Test
    public void shouldReturnFinalAmount() throws Exception {

        double principle = 2000;
        double noOfYears = 5;
        double rate = 3;
        DealDetails dealDetails = new DealDetails(principle, noOfYears, rate);

        CalculatorResponse result = underTest.calculate(dealDetails);

        assertThat(String.format("%.2f", result.getAmount()), is("318.55"));
        assertThat(String.format("%.2f", result.getConverted()), is("411.82"));
    }

}
