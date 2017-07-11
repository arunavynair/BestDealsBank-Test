package com.hsbc.bestdealsbank.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hsbc.bestdealsbank.domain.DealDetails;

public interface Dao {

    static final String USD = "USD";
    static final String EURO = "Euro";
    static Map<String, List<DealDetails>> clientDeals = new HashMap<>();

    Double convert(String currency, Double amount);
    List<DealDetails> getAllDealsForClient(String clientId);
    void putClientDeals (String clientID, List<DealDetails> deals);

    default Currency[] getCurrencies() {
        final Currency[] Currencies = {
                        new Currency(USD, 1.2928),
                        new Currency(EURO, 0.86391)
        };
        return Currencies;
    }
    
    default List<DealDetails> getClientDeals (String clientID) {
        List<DealDetails> list = clientDeals.get(clientID);
        if (null == list) {
            return new ArrayList<>();
        }
        return list;
    }

}