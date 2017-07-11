package com.hsbc.bestdealsbank.service.decorator;

import static com.hsbc.bestdealsbank.dao.Dao.USD;

public class UsdExchangeRateDecorator extends ExchangeRateDecorator {

    public Double convert(Double amount) {
        return dao.convert(USD, amount);
    }

}
