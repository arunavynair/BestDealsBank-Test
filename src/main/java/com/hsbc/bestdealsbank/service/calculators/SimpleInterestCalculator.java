package com.hsbc.bestdealsbank.service.calculators;

import javax.inject.Inject;
import javax.inject.Named;

import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.DealDetails;
import com.hsbc.bestdealsbank.service.decorator.ExchangeRateDecorator;

@Named("simple")
public class SimpleInterestCalculator implements InterestCalculator {

    private final ExchangeRateDecorator decorator;

    @Inject
    public SimpleInterestCalculator(ExchangeRateDecorator decorator) {
        this.decorator = decorator;
    }

    @Override
    public CalculatorResponse calculate(DealDetails dealDetails) {

        double amount = dealDetails.getPrinciple() * dealDetails.getNoOfYears() * dealDetails.getRate() / 100;
        Double converted = decorator.convert(amount);
        
        return new CalculatorResponse(amount, converted);
    }

}
