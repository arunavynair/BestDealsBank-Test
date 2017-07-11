package com.hsbc.bestdealsbank.resource;

import java.util.List;

import javax.ws.rs.PathParam;

import com.hsbc.bestdealsbank.domain.CalculatorResponse;
import com.hsbc.bestdealsbank.domain.CalculatorType;
import com.hsbc.bestdealsbank.domain.DealDetails;

public interface CalculatorService {

    CalculatorResponse calculate(String calculatorType, DealDetails dealDetails);
    
    List<CalculatorResponse> getAllDealsForClient(@PathParam("clientId") String clientId);
    
    void putClientDeals(String clientID, List<DealDetails> deals);
}
