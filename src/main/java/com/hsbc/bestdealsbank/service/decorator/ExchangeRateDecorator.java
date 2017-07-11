package com.hsbc.bestdealsbank.service.decorator;

import javax.inject.Inject;

public abstract class ExchangeRateDecorator {
    
    @Inject
    public com.hsbc.bestdealsbank.dao.Dao dao;
    
    public abstract Double convert(Double amount);
}
