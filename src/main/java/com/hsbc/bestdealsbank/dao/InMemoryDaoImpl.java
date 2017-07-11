package com.hsbc.bestdealsbank.dao;

import java.util.Arrays;
import java.util.List;

import com.hsbc.bestdealsbank.domain.DealDetails;

public final class InMemoryDaoImpl implements Dao {

    @Override
    public Double convert(String currency, Double amount) {
        return Arrays.stream(getCurrencies())
                        .filter(cur -> cur.getCurrencyName().equals(currency))
                        .findFirst()
                        .map(cu -> cu.getCurrencyRate() * amount)
                        .orElse(null);
    }

    @Override
    public List<DealDetails> getAllDealsForClient(String clientId) {
        return getClientDeals(clientId);
    }

    @Override
    public void putClientDeals(String clientID, List<DealDetails> deals) {
        clientDeals.put(clientID, deals);
    }
}
