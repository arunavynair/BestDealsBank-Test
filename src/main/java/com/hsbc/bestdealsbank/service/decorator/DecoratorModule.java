package com.hsbc.bestdealsbank.service.decorator;

import com.google.inject.AbstractModule;
import com.hsbc.bestdealsbank.domain.CalculatorResponse;

public class DecoratorModule extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(CalculatorResponse.class);
        bind(ExchangeRateDecorator.class).toInstance(new UsdExchangeRateDecorator());

    }

}
