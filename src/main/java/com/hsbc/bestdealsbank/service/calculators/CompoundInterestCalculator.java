package com.hsbc.bestdealsbank.service.calculators;

import javax.inject.Inject;
import javax.inject.Named;

import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.DealDetails;
import com.hsbc.bestdealsbank.service.decorator.ExchangeRateDecorator;

@Named("compound")
public class CompoundInterestCalculator implements InterestCalculator {

    private final ExchangeRateDecorator decorator;

    @Inject
    public CompoundInterestCalculator(ExchangeRateDecorator decorator) {
        this.decorator = decorator;
    }

    @Override
    public CalculatorResponse calculate(DealDetails deals) {

        double amount = deals.getPrinciple() * Math.pow(1.0 + deals.getRate() / 100.0, deals.getNoOfYears()) - deals.getPrinciple();
        Double converted = decorator.convert(amount);
        
        return new CalculatorResponse(amount, converted);
    }


}
