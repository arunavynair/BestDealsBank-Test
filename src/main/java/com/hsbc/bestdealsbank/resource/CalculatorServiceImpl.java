package com.hsbc.bestdealsbank.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hsbc.bestdealsbank.dao.Dao;
import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.CalculatorType;
import com.hsbc.bestdealsbank.domain.DealDetails;
import com.hsbc.bestdealsbank.service.calculators.InterestCalculator;

public class CalculatorServiceImpl implements CalculatorService {

    private static final Logger LOG = LoggerFactory.getLogger(CalculatorService.class);
    private static final CalculatorType SIMPLE = CalculatorType.valueOf("simple");
    private static final CalculatorType COMPOUND = CalculatorType.valueOf("compound");

    private final InterestCalculator compoundInterest;
    private final InterestCalculator simpleInterestCalculator;
    private final Dao dao;

    @Inject
    public CalculatorServiceImpl(@Named("simple") Provider<InterestCalculator> simpleInterestCalculator,
                    @Named("compound") Provider<InterestCalculator> compoundInterest,
                    Dao dao) {
        this.compoundInterest = compoundInterest.get();
        this.simpleInterestCalculator = simpleInterestCalculator.get();
        this.dao = dao;
    }

    @Override
    public CalculatorResponse calculate(String calculatorType, DealDetails dealDetails) {
        LOG.info("> DealDetails & calculatorType {}, {}", dealDetails.toString(), calculatorType);
        
        return getCalculator(getCalculatorType (calculatorType)).calculate(dealDetails);
    }

    private InterestCalculator getCalculator(CalculatorType calculatorType) {

        if (calculatorType.equals(SIMPLE)) {
            return this.simpleInterestCalculator;
        } else if (calculatorType.equals(COMPOUND)) {
            return this.compoundInterest;
        } else {
            throw new IllegalArgumentException("Unknown Calculator Type " + calculatorType);
        }
    }

    @Override
    public List<CalculatorResponse> getAllDealsForClient(String clientId) {
        List<CalculatorResponse> calculatorResponses = new ArrayList<>();

        List<DealDetails> allDealsForClient = this.dao.getAllDealsForClient(clientId);
        
        allDealsForClient.forEach(deal -> {
            calculatorResponses.add(getCalculator(CalculatorType.simple).calculate(deal));
            calculatorResponses.add(getCalculator(CalculatorType.compound).calculate(deal));
        });

        return calculatorResponses;
    }

    @Override
    public void putClientDeals(String clientID, List<DealDetails> deals) {
        this.dao.putClientDeals(clientID, deals);
    }
    
    private CalculatorType getCalculatorType(String calculatorType) {
        return Arrays.stream(CalculatorType.values())
                        .filter(type -> type.name().equalsIgnoreCase(calculatorType.toLowerCase()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported : calculator Type %s.", calculatorType)));
    }

}
