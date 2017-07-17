package com.hsbc.bestdealsbank.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hsbc.bestdealsbank.dao.Dao;
import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.CalculatorType;
import com.hsbc.bestdealsbank.domain.DealDetails;
import com.hsbc.bestdealsbank.service.calculators.InterestCalculator;

public class CalculatorServiceImpl implements CalculatorService {

    private static final Logger LOG = LoggerFactory.getLogger(CalculatorService.class);

    private final Map<String, InterestCalculator> calculators;
    private final Dao dao;

    @Inject
    public CalculatorServiceImpl(Dao dao, Map<String, InterestCalculator> calculators) {
        this.dao = dao;
        this.calculators = calculators;
    }

    @Override
    public CalculatorResponse calculate(String calculatorType, DealDetails dealDetails) {
        LOG.info("> DealDetails & calculatorType {}, {}", dealDetails.toString(), calculatorType);

        return getCalculator(getCalculatorType(calculatorType)).calculate(dealDetails);
    }

    @Override
    public List<CalculatorResponse> getAllDealsForClient(String clientId) {
        List<CalculatorResponse> calculatorResponses = new ArrayList<>();
        
        this.dao.getAllDealsForClient(clientId).forEach(deal -> {
            calculatorResponses.add(getCalculator(CalculatorType.simple).calculate(deal));
            calculatorResponses.add(getCalculator(CalculatorType.compound).calculate(deal));
        });

        return calculatorResponses;
    }

    @Override
    public void putClientDeals(String clientID, List<DealDetails> deals) {
        this.dao.putClientDeals(clientID, deals);
    }

    private InterestCalculator getCalculator(CalculatorType calculatorType) {
        return calculators.keySet().stream()
                        .filter(calculatorType.name()::equalsIgnoreCase)
                        .map(calculators::get)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown Calculator Type " + calculatorType));
    }

    private CalculatorType getCalculatorType(String calculatorType) {
        return Arrays.stream(CalculatorType.values())
                        .filter(type -> type.name().equalsIgnoreCase(calculatorType))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported : calculator Type %s.", calculatorType)));
    }

}
