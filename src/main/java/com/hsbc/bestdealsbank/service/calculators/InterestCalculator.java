package com.hsbc.bestdealsbank.service.calculators;

import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.DealDetails;

@FunctionalInterface
public interface InterestCalculator {
    
    CalculatorResponse calculate(DealDetails deals);
}
